package application;

import java.lang.reflect.Field;

public abstract class CustumClass {

	private static String tableName="";

    private Integer id;


    public Integer getId() {
        return id;
    }
    public static String getTableName() {
        return tableName;
    }
    public static void setTableName(String tableName) {
        CustumClass.tableName = tableName;
    }
    public void setId(Integer id) {
        this.id = id;
    }














    @Override
    public String toString() {
        Field[] fields = this.getClass().getFields();
        String r="";
		for (Field f :fields) {
            try {
                r+=f.getName()+"="+f.get(this)+",";
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        r=r.substring(0,r.length()-1);
        return this.getClass().getName()+"<"+r+">";
    }
}
