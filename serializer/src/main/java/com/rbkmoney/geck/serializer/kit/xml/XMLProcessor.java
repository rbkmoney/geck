package com.rbkmoney.geck.serializer.kit.xml;

import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.StructProcessor;
import com.rbkmoney.geck.serializer.exception.BadFormatException;
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
            String type = node.getAttribute(XMLConstants.ATTRIBUTE_TYPE);
            switch (type) {
                case XMLConstants.STRING:
                    handler.value(node.getTextContent());
                    break;
                case XMLConstants.BOOL:
                    handler.value(Boolean.valueOf(node.getTextContent()));
                    break;
                case XMLConstants.BYTEARRAY:
                    handler.value(Base64.getDecoder().decode(node.getTextContent()));
                    break;
                case XMLConstants.DOUBLE:
                    handler.value(Double.valueOf(node.getTextContent()));
                    break;
                case XMLConstants.LONG:
                    handler.value(Long.valueOf(node.getTextContent()));
                    break;
                case XMLConstants.NULL:
                    handler.nullValue();
                    break;
                case "struct" :
                    handler.beginStruct(Integer.parseInt(node.getAttribute(XMLConstants.ATTRIBUTE_SIZE)));
                    processChildNodes(node, handler, true);
                    handler.endStruct();
                    break;
                case "list" :
                    handler.beginList(Integer.parseInt(node.getAttribute(XMLConstants.ATTRIBUTE_SIZE)));
                    processChildNodes(node, handler, false);
                    handler.endList();
                    break;
                case "set" :
                    handler.beginSet(Integer.parseInt(node.getAttribute(XMLConstants.ATTRIBUTE_SIZE)));
                    processChildNodes(node, handler, false);
                    handler.endSet();
                    break;
                case "map" :
                    handler.beginMap(Integer.parseInt(node.getAttribute(XMLConstants.ATTRIBUTE_SIZE)));
                    processChildNodes(node, handler, false);
                    handler.endMap();
                    break;
                case XMLConstants.MAP_ENTRY :
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
