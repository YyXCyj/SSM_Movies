package com.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.RemendianyingEntity;
import com.entity.view.RemendianyingView;

import com.service.RemendianyingService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;
import com.service.StoreupService;
import com.entity.StoreupEntity;

/**
 * 热门电影
 * 后端接口
 * @author 
 * @email 
 * @date 2022-05-06 17:35:10
 */
@RestController
@RequestMapping("/remendianying")
public class RemendianyingController {
    @Autowired
    private RemendianyingService remendianyingService;


    @Autowired
    private StoreupService storeupService;

    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,RemendianyingEntity remendianying, 
		HttpServletRequest request){

        EntityWrapper<RemendianyingEntity> ew = new EntityWrapper<RemendianyingEntity>();
		PageUtils page = remendianyingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, remendianying), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,RemendianyingEntity remendianying, 
		HttpServletRequest request){
        EntityWrapper<RemendianyingEntity> ew = new EntityWrapper<RemendianyingEntity>();
		PageUtils page = remendianyingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, remendianying), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( RemendianyingEntity remendianying){
       	EntityWrapper<RemendianyingEntity> ew = new EntityWrapper<RemendianyingEntity>();
      	ew.allEq(MPUtil.allEQMapPre( remendianying, "remendianying")); 
        return R.ok().put("data", remendianyingService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(RemendianyingEntity remendianying){
        EntityWrapper< RemendianyingEntity> ew = new EntityWrapper< RemendianyingEntity>();
 		ew.allEq(MPUtil.allEQMapPre( remendianying, "remendianying")); 
		RemendianyingView remendianyingView =  remendianyingService.selectView(ew);
		return R.ok("查询热门电影成功").put("data", remendianyingView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        RemendianyingEntity remendianying = remendianyingService.selectById(id);
		remendianying.setClicknum(remendianying.getClicknum()+1);
		remendianying.setClicktime(new Date());
		remendianyingService.updateById(remendianying);
        return R.ok().put("data", remendianying);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        RemendianyingEntity remendianying = remendianyingService.selectById(id);
		remendianying.setClicknum(remendianying.getClicknum()+1);
		remendianying.setClicktime(new Date());
		remendianyingService.updateById(remendianying);
        return R.ok().put("data", remendianying);
    }
    


    /**
     * 赞或踩
     */
    @RequestMapping("/thumbsup/{id}")
    public R thumbsup(@PathVariable("id") String id,String type){
        RemendianyingEntity remendianying = remendianyingService.selectById(id);
        if(type.equals("1")) {
        	remendianying.setThumbsupnum(remendianying.getThumbsupnum()+1);
        } else {
        	remendianying.setCrazilynum(remendianying.getCrazilynum()+1);
        }
        remendianyingService.updateById(remendianying);
        return R.ok();
    }

    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody RemendianyingEntity remendianying, HttpServletRequest request){
    	remendianying.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(remendianying);

        remendianyingService.insert(remendianying);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody RemendianyingEntity remendianying, HttpServletRequest request){
    	remendianying.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(remendianying);

        remendianyingService.insert(remendianying);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody RemendianyingEntity remendianying, HttpServletRequest request){
        //ValidatorUtils.validateEntity(remendianying);
        remendianyingService.updateById(remendianying);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        remendianyingService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<RemendianyingEntity> wrapper = new EntityWrapper<RemendianyingEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}


		int count = remendianyingService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	
	/**
     * 前端智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,RemendianyingEntity remendianying, HttpServletRequest request,String pre){
        EntityWrapper<RemendianyingEntity> ew = new EntityWrapper<RemendianyingEntity>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = entry.getKey();
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		params.put("sort", "clicknum");
        
        params.put("order", "desc");
		PageUtils page = remendianyingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, remendianying), params), params));
        return R.ok().put("data", page);
    }


    /**
     * 协同算法（按收藏推荐）
     */
    @RequestMapping("/autoSort2")
    public R autoSort2(@RequestParam Map<String, Object> params,RemendianyingEntity remendianying, HttpServletRequest request){
        String userId = request.getSession().getAttribute("userId").toString();
        String inteltypeColumn = "dianyingleixing";
        List<StoreupEntity> storeups = storeupService.selectList(new EntityWrapper<StoreupEntity>().eq("type", 1).eq("userid", userId).eq("tablename", "remendianying").orderBy("addtime", false));
        List<String> inteltypes = new ArrayList<String>();
        Integer limit = params.get("limit")==null?10:Integer.parseInt(params.get("limit").toString());
        List<RemendianyingEntity> remendianyingList = new ArrayList<RemendianyingEntity>();
        //去重
        if(storeups!=null && storeups.size()>0) {
            for(StoreupEntity s : storeups) {
                remendianyingList.addAll(remendianyingService.selectList(new EntityWrapper<RemendianyingEntity>().eq(inteltypeColumn, s.getInteltype())));
            }
        }
        EntityWrapper<RemendianyingEntity> ew = new EntityWrapper<RemendianyingEntity>();
        params.put("sort", "id");
        params.put("order", "desc");
        PageUtils page = remendianyingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, remendianying), params), params));
        List<RemendianyingEntity> pageList = (List<RemendianyingEntity>)page.getList();
        if(remendianyingList.size()<limit) {
            int toAddNum = (limit-remendianyingList.size())<=pageList.size()?(limit-remendianyingList.size()):pageList.size();
            for(RemendianyingEntity o1 : pageList) {
                boolean addFlag = true;
                for(RemendianyingEntity o2 : remendianyingList) {
                    if(o1.getId().intValue()==o2.getId().intValue()) {
                        addFlag = false;
                        break;
                    }
                }
                if(addFlag) {
                    remendianyingList.add(o1);
                    if(--toAddNum==0) break;
                }
            }
        } else if(remendianyingList.size()>limit) {
            remendianyingList = remendianyingList.subList(0, limit);
        }
        page.setList(remendianyingList);
        return R.ok().put("data", page);
    }





}
