package com.rbkmoney.kebab.thrift;

/**
 * Created by tolkonepiu on 24/01/2017.
 */
public abstract class ThriftElement {

    public boolean isThriftNull() {
        return this instanceof ThriftNull;
    }

//    public ThriftNull getAsThriftNull() {
//        if (isThriftNull()) {
//            return (ThriftNull) this;
//        }
//        throw new IllegalStateException("this is not a Thrift Null");
//    }

    public boolean isThriftObject() {
        return this instanceof ThriftObject;
    }

//    public ThriftObject getAsThriftObject() {
//        if (isThriftObject()) {
//            return (ThriftObject) this;
//        }
//        throw new IllegalStateException("this is not a Thrift Object: " + this);
//    }

    public boolean isThriftList() {
        return this instanceof ThriftList;
    }

//    public ThriftList getAsThriftList() {
//        if (isThriftList()) {
//            return (ThriftList) this;
//        }
//        throw new IllegalStateException("this is not a Thrift List");
//    }

    public boolean isThriftSet() {
        return this instanceof ThriftSet;
    }

//    public ThriftSet getAsThriftSet() {
//        if (isThriftSet()) {
//            return (ThriftSet) this;
//        }
//        throw new IllegalStateException("this is not a Thrift Set");
//    }

    public boolean isThriftMap() {
        return this instanceof ThriftMap;
    }

//    public ThriftMap getAsThriftMap() {
//        if (isThriftMap()) {
//            return (ThriftMap) this;
//        }
//        throw new IllegalStateException("this is not a Thrift Map");
//    }

    public boolean isThriftMapEntry() {
        return this instanceof ThriftMapEntry;
    }

//    public ThriftMapEntry getAsThriftMapEntry() {
//        if (isThriftMapEntry()) {
//            return (ThriftMapEntry) this;
//        }
//        throw new IllegalStateException("this is not a Thrift Map Entry");
//    }


    public boolean isThriftPrimitive() {
        return this instanceof ThriftPrimitive;
    }

//    public ThriftPrimitive getAsThriftPrimitive() {
//        if (isThriftPrimitive()) {
//            return (ThriftPrimitive) this;
//        }
//        throw new IllegalStateException("this is not a Thrift Primitive");
//    }

}
