package com.github.anhem.testpopulator.verification.model;

public class MyJavaPojoWithKotlinField {
    private final MyDataClass myDataClass;
    private final MyJavaEnum myJavaEnum;

    public MyJavaPojoWithKotlinField(MyDataClass myDataClass, MyJavaEnum myJavaEnum) {
        this.myDataClass = myDataClass;
        this.myJavaEnum = myJavaEnum;
    }

    public MyDataClass getMyDataClass() {
        return myDataClass;
    }

    public MyJavaEnum getMyJavaEnum() {
        return myJavaEnum;
    }
}
