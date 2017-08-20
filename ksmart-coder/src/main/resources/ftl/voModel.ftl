package ${package};

import com.excellence.platform.portal.common.base.BaseDao;
import com.excellence.platform.portal.cms.vo.${classPre}VO;

/**
* @author masb
* 表${ tableName }对应dao操作:实现简单的增删改查,分页等基本操作
*
*/

public class ${classPre}Dao extends BaseDao{

// 无参构造方法
public ${classPre}Dao() {
super();
this.setTableName("${ tableName }");
this.setKeyName("id");
this.setVoClassName(${classPre}VO.class);
}

public ${classPre}Dao(String table, String key) {
super(table, key);
this.setVoClassName(${classPre}VO.class);
}

public ${classPre}Dao(String table, String key, String dsJDNI) {
super(table, key, dsJDNI);
this.setVoClassName(${classPre}VO.class);
}

public ${classPre}Dao(String table, String key, String dsJDNI, Class voClass) {
super(table, key, dsJDNI, voClass);
}
}