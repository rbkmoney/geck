package com.rbkmoney.geck.serializer.domain;

import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;

import javax.annotation.Generated;
import java.util.*;

@Generated(value = "Autogenerated by Thrift Compiler (1.0.0-dev)", date = "2020-07-09")
public class FilterMapObject implements org.apache.thrift.TBase<FilterMapObject, FilterMapObject._Fields>, java.io.Serializable, Cloneable, Comparable<FilterMapObject> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("FilterMapObject");

  private static final org.apache.thrift.protocol.TField MAP1_FIELD_DESC = new org.apache.thrift.protocol.TField("map1", org.apache.thrift.protocol.TType.MAP, (short)1);

  private static final SchemeFactory STANDARD_SCHEME_FACTORY = new FilterMapObjectStandardSchemeFactory();
  private static final SchemeFactory TUPLE_SCHEME_FACTORY = new FilterMapObjectTupleSchemeFactory();

  public Map<String,Unknown> map1; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    MAP1((short)1, "map1");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // MAP1
          return MAP1;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.MAP1, new org.apache.thrift.meta_data.FieldMetaData("map1", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Unknown.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(FilterMapObject.class, metaDataMap);
  }

  public FilterMapObject() {
  }

  public FilterMapObject(
    Map<String,Unknown> map1)
  {
    this();
    this.map1 = map1;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public FilterMapObject(FilterMapObject other) {
    if (other.isSetMap1()) {
      Map<String,Unknown> __this__map1 = new HashMap<String,Unknown>(other.map1.size());
      for (Map.Entry<String, Unknown> other_element : other.map1.entrySet()) {

        String other_element_key = other_element.getKey();
        Unknown other_element_value = other_element.getValue();

        String __this__map1_copy_key = other_element_key;

        Unknown __this__map1_copy_value = new Unknown(other_element_value);

        __this__map1.put(__this__map1_copy_key, __this__map1_copy_value);
      }
      this.map1 = __this__map1;
    }
  }

  public FilterMapObject deepCopy() {
    return new FilterMapObject(this);
  }

  @Override
  public void clear() {
    this.map1 = null;
  }

  public int getMap1Size() {
    return (this.map1 == null) ? 0 : this.map1.size();
  }

  public void putToMap1(String key, Unknown val) {
    if (this.map1 == null) {
      this.map1 = new HashMap<String,Unknown>();
    }
    this.map1.put(key, val);
  }

  public Map<String,Unknown> getMap1() {
    return this.map1;
  }

  public FilterMapObject setMap1(Map<String,Unknown> map1) {
    this.map1 = map1;
    return this;
  }

  public void unsetMap1() {
    this.map1 = null;
  }

  /** Returns true if field map1 is set (has been assigned a value) and false otherwise */
  public boolean isSetMap1() {
    return this.map1 != null;
  }

  public void setMap1IsSet(boolean value) {
    if (!value) {
      this.map1 = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case MAP1:
      if (value == null) {
        unsetMap1();
      } else {
        setMap1((Map<String,Unknown>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case MAP1:
      return getMap1();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case MAP1:
      return isSetMap1();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof FilterMapObject)
      return this.equals((FilterMapObject)that);
    return false;
  }

  public boolean equals(FilterMapObject that) {
    if (that == null)
      return false;

    boolean this_present_map1 = true && this.isSetMap1();
    boolean that_present_map1 = true && that.isSetMap1();
    if (this_present_map1 || that_present_map1) {
      if (!(this_present_map1 && that_present_map1))
        return false;
      if (!this.map1.equals(that.map1))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetMap1()) ? 131071 : 524287);
    if (isSetMap1())
      hashCode = hashCode * 8191 + map1.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(FilterMapObject other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetMap1()).compareTo(other.isSetMap1());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMap1()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.map1, other.map1);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public _Fields[] getFields() {
    return _Fields.values();
  }

  public Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> getFieldMetaData() {
    return metaDataMap;
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("FilterMapObject(");
    boolean first = true;

    sb.append("map1:");
    if (this.map1 == null) {
      sb.append("null");
    } else {
      sb.append(this.map1);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (map1 == null) {
      throw new TProtocolException("Required field 'map1' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class FilterMapObjectStandardSchemeFactory implements SchemeFactory {
    public FilterMapObjectStandardScheme getScheme() {
      return new FilterMapObjectStandardScheme();
    }
  }

  private static class FilterMapObjectStandardScheme extends StandardScheme<FilterMapObject> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, FilterMapObject struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // MAP1
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map192 = iprot.readMapBegin();
                struct.map1 = new HashMap<String,Unknown>(2*_map192.size);
                String _key193;
                Unknown _val194;
                for (int _i195 = 0; _i195 < _map192.size; ++_i195)
                {
                  _key193 = iprot.readString();
                  _val194 = new Unknown();
                  _val194.read(iprot);
                  struct.map1.put(_key193, _val194);
                }
                iprot.readMapEnd();
              }
              struct.setMap1IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, FilterMapObject struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.map1 != null) {
        oprot.writeFieldBegin(MAP1_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, struct.map1.size()));
          for (Map.Entry<String, Unknown> _iter196 : struct.map1.entrySet())
          {
            oprot.writeString(_iter196.getKey());
            _iter196.getValue().write(oprot);
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class FilterMapObjectTupleSchemeFactory implements SchemeFactory {
    public FilterMapObjectTupleScheme getScheme() {
      return new FilterMapObjectTupleScheme();
    }
  }

  private static class FilterMapObjectTupleScheme extends TupleScheme<FilterMapObject> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, FilterMapObject struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      {
        oprot.writeI32(struct.map1.size());
        for (Map.Entry<String, Unknown> _iter197 : struct.map1.entrySet())
        {
          oprot.writeString(_iter197.getKey());
          _iter197.getValue().write(oprot);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, FilterMapObject struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      {
        org.apache.thrift.protocol.TMap _map198 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.map1 = new HashMap<String,Unknown>(2*_map198.size);
        String _key199;
        Unknown _val200;
        for (int _i201 = 0; _i201 < _map198.size; ++_i201)
        {
          _key199 = iprot.readString();
          _val200 = new Unknown();
          _val200.read(iprot);
          struct.map1.put(_key199, _val200);
        }
      }
      struct.setMap1IsSet(true);
    }
  }

  private static <S extends IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

