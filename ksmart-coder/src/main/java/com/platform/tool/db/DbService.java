package com.platform.tool.db;

import java.util.List;

import ksmart.coder.model.FieldBean;

public interface DbService {

	/**根据表名获取所有的列信息
	 * @param tableName
	 * @return
	 */
	public List<FieldBean> getAllColums(String tableName);

}
