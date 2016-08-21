package com.fishpan.ioc.compiler;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Created by yupan on 16/7/23.
 */
public class ProxyInfo {
    public static final String SUFFIX = "ViewInjector";
    public Map<Integer, VariableElement> mInjectElements = new HashMap<>();
    private String mPackageName;
    private String mProxyClassName;
    private TypeElement mTypeElement;
    public ProxyInfo(Elements elements, TypeElement typeElement){
        mTypeElement = typeElement;
        mPackageName = getPackageName(elements, typeElement);
        mProxyClassName = getClassName(mPackageName, typeElement);
    }

    private String getPackageName(Elements elements, TypeElement type) {
        return elements.getPackageOf(type).getQualifiedName().toString();
    }

    private String getClassName(String packName, TypeElement type){
        return type.getQualifiedName().toString().substring(packName.length() + 1) + "$$" + SUFFIX;
    }

    public String generateJavaCode(){
        StringBuilder builder = new StringBuilder();
        builder.append("package " + mPackageName).append(";\n\n");
        builder.append("import com.fishpan.ioc.api.*;\n");
        builder.append("public class ").append(mProxyClassName).append(" implements " + SUFFIX + "<" + mTypeElement.getQualifiedName() + ">");
        builder.append("\n{\n");
        generateMethod(builder);
        builder.append("\n}\n");
        return builder.toString();
    }
    private void generateMethod(StringBuilder builder){
        builder.append("public void inject("+mTypeElement.getQualifiedName()+" host , Object object )");
        builder.append("\n{\n");
        for(int id : mInjectElements.keySet()){
            VariableElement variableElement = mInjectElements.get(id);
            String name = variableElement.getSimpleName().toString();
            String type = variableElement.asType().toString() ;

            builder.append(" if(object instanceof android.app.Activity)");
            builder.append("\n{\n");
            builder.append("host."+name).append(" = ");
            builder.append("("+type+")(((android.app.Activity)object).findViewById("+id+"));");
            builder.append("\n}\n").append("else").append("\n{\n");
            builder.append("host."+name).append(" = ");
            builder.append("("+type+")(((android.view.View)object).findViewById("+id+"));");
            builder.append("\n}\n");
        }
        builder.append("\n}\n");
    }

    public TypeElement getTypeElement(){
        return mTypeElement;
    }

    public String getProxyClassFullName(){
        return mPackageName + "." + mProxyClassName;
    }
}
