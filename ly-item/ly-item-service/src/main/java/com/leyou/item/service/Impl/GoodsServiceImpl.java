package com.leyou.item.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyou.item.bo.SpuBo;
import com.leyou.common.po.PageResult;
import com.leyou.item.mapper.*;
import com.leyou.item.service.CategoryService;
import com.leyou.item.service.GoodsService;
import com.leyou.item.pojo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 17:09 2020/1/3
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDatilMapper spuDatilMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public PageResult<SpuBo> querySpuByPage(String key, Boolean saleable, Integer page, Integer rows) {
        //开启分页
        PageHelper.startPage(page,rows);

        Example example=new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title","%"+key+"%");
        }
        if(null!=saleable){
            criteria.andEqualTo("saleable",saleable);
        }
        //查询结果
        Page<Spu> spus = (Page<Spu>)spuMapper.selectByExample(example);
        List<Spu> result = spus.getResult();

        List<SpuBo> spuBos=new ArrayList<SpuBo>();
        for (Spu s:result){
            SpuBo spuBo=new SpuBo();
            BeanUtils.copyProperties(s,spuBo);
            List<String> names=categoryService.queryNamesByIds(Arrays.asList(s.getCid1(),s.getCid2(),s.getCid3()));
            String join=StringUtils.join(names,"/");
            spuBo.setCname(join);
            Brand brand = brandMapper.selectByPrimaryKey(s.getBrandId());
            spuBo.setBname(brand.getName());
            spuBos.add(spuBo);
        }
        return new PageResult<>(spus.getTotal(),new Long(spus.getPages()),spuBos);
    }

    @Override
    public void saveGoods(SpuBo spuBo) {
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(new Date());
        spuMapper.insertSelective(spuBo);
        //保存spudetail表
        Long sid=spuBo.getId();
        SpuDetail spuDetail=spuBo.getSpuDetail();
        spuDetail.setSpuId(sid);
        this.spuDatilMapper.insert(spuDetail);

        List<Sku> skuList=spuBo.getSkus();
        saveSkus(spuBo,skuList);

        //发送消息
        sendMessage("insert", sid);

    }

    @Override
    public SpuDetail querySpudetailByspuId(Long id) {
        return spuDatilMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Sku> querySkuBySpuId(Long spuid) {
        Sku sku=new Sku();
        sku.setSpuId(spuid);
        List<Sku> skus=skuMapper.select(sku);
        skus.forEach(t->{
            Long id=t.getId();
            Stock stock = this.stockMapper.selectByPrimaryKey(id);
            t.setStock(stock.getStock());
        });
        return skus;
    }

    @Override
    @Transactional
    public void updateGoods(SpuBo spuBo) {
       spuBo.setLastUpdateTime(new Date());
       //更新spu表
       this.spuMapper.updateByPrimaryKey(spuBo);

       SpuDetail spuDetail=spuBo.getSpuDetail();
        //更新spuDetail表
       this.spuDatilMapper.updateByPrimaryKey(spuDetail);

       Long id=spuBo.getId();
        Sku sku = new Sku();
        sku.setSpuId(id);
        List<Sku> skulist = this.skuMapper.select(sku);

        for (Sku s:skulist){
            this.skuMapper.delete(s);
            this.stockMapper.deleteByPrimaryKey(s.getId());

        }
        saveSkus(spuBo,spuBo.getSkus());
        //发送消息
        sendMessage("update", id);
    }

    @Override
    public Spu querySpuById(Long spuId) {
        return this.spuMapper.selectByPrimaryKey(spuId);
    }

    private void saveSkus(SpuBo spuBo, List<Sku> skuList) {
        for (Sku sku:skuList){

            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(new Date());
            this.skuMapper.insertSelective(sku);

            Stock stock=new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            this.stockMapper.insert(stock);
        }
    }
    //发送消息
    public void sendMessage(String type,Long id){
        this.amqpTemplate.convertAndSend("item."+type,id);
    }
}
