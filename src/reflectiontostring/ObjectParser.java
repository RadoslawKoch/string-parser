package reflectiontostring;

import java.lang.reflect.Field;

/**
 * @author Radosław Koch
 */
public class ObjectParser {

    public static String getStringFromObject(Object obj) throws IllegalArgumentException, IllegalAccessException{
        Class<?> objClass = obj.getClass();
        StringBuilder builder = new StringBuilder(objClass.getSimpleName()).append("{");
        while(objClass!=null && objClass!=Object.class){
            Field[] fields = objClass.getDeclaredFields();
            for(int i = 0;i < fields.length;i++){
                Field x= fields[i];
                x.setAccessible(true);
                if(x.getType().isPrimitive()){
                    builder.append(fieldToString(x,obj));
                }else{
                    builder.append(getStringFromObject(x.get(obj)));
                }
                if(i<fields.length-1){
                    builder.append(",");
                }
            }
            objClass = objClass.getSuperclass();
            if(objClass!=null && objClass.getDeclaredFields().length>0){
                builder.append(",");
            }
        }
        return builder.append("}").toString();
    }
    
    //HELPER METHOD
    private static String fieldToString(Field x, Object obj) throws IllegalArgumentException, IllegalAccessException{
        return new StringBuilder().append("[")
                            .append(x.getType().getSimpleName())
                            .append("]")
                            .append(x.getName())
                            .append("=")
                            .append(x.get(obj)).toString();
    }
    
}
