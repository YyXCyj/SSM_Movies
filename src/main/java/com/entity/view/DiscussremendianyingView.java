package com.entity.view;

import com.entity.DiscussremendianyingEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
 

/**
 * 热门电影评论表
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2022-05-06 17:35:11
 */
@TableName("discussremendianying")
public class DiscussremendianyingView  extends DiscussremendianyingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public DiscussremendianyingView(){
	}
 
 	public DiscussremendianyingView(DiscussremendianyingEntity discussremendianyingEntity){
 	try {
			BeanUtils.copyProperties(this, discussremendianyingEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}
}
