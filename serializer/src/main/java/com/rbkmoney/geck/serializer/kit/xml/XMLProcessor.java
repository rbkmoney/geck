package com.rbkmoney.geck.serializer.kit.xml;

import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.StructProcessor;
import com.rbkmoney.geck.serializer.exception.BadFormatException;
import com.rbkmoney.geck.serializer.kit.StructType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.dom.DOMResult;
import java.io.IOException;
import java.util.Base64;

/**
 * Created by inalarsanukaev on 16.03.17.
 */
public class XMLProcessor implements StructProcessor<DOMResult> {
    @Override
    public <R> R process(DOMResult value, StructHandler<R> handler) throws IOException {
        Node document = value.getNode().getFirstChild();
        String nodeName = document.getNodeName();
        if (!nodeName.equals(XMLConstants.ROOT)) {
            throw new BadFormatException("Root element must have name '"+XMLConstants.ROOT+"'");
        }
        processNode((Element) document, handler, false);
        return handler.getResult();
    }

    protected void processNode(Element node, StructHandler handler, boolean printName) throws IOException {
        if (node != null){
            String nodeName = node.getNodeName();
            if (printName) {
                handler.name(nodeName);
            }
            StructType type = StructType.valueOfKey(node.getAttribute(XMLConstants.ATTRIBUTE_TYPE));
            switch (type) {
                case STRING:
                    handler.value(node.getTextContent());
                    break;
                case BOOL:
                    handler.value(Boolean.valueOf(node.getTextContent()));
                    break;
                case BYTEARRAY:
                    handler.value(Base64.getDecoder().decode(node.getTextContent()));
                    break;
                case DOUBLE:
                    handler.value(Double.valueOf(node.getTextContent()));
                    break;
                case LONG:
                    handler.value(Long.valueOf(node.getTextContent()));
                    break;
                case NULL:
                    handler.nullValue();
                    break;
                case STRUCT:
                    handler.beginStruct(Integer.parseInt(node.getAttribute(XMLConstants.ATTRIBUTE_SIZE)));
                    processChildNodes(node, handler, true);
                    handler.endStruct();
                    break;
                case LIST:
                    handler.beginList(Integer.parseInt(node.getAttribute(XMLConstants.ATTRIBUTE_SIZE)));
                    processChildNodes(node, handler, false);
                    handler.endList();
                    break;
                case SET:
                    handler.beginSet(Integer.parseInt(node.getAttribute(XMLConstants.ATTRIBUTE_SIZE)));
                    processChildNodes(node, handler, false);
                    handler.endSet();
                    break;
                case MAP:
                    handler.beginMap(Integer.parseInt(node.getAttribute(XMLConstants.ATTRIBUTE_SIZE)));
                    processChildNodes(node, handler, false);
                    handler.endMap();
                    break;
                case MAP_ENTRY :
                    handler.beginKey();
                    processNode((Element) node.getFirstChild(), handler, false);
                    handler.endKey();
                    handler.beginValue();
                    processNode((Element) node.getLastChild(), handler, false);
                    handler.endValue();
                    break;
                default:
                    new BadFormatException("Unknown type of node: "+type);
            }
        }
    }
    private void processChildNodes(Element node, StructHandler handler, boolean printName) throws IOException {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node item = nodeList.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                processNode((Element) item, handler, printName);
            }
        }
    }
}
