package com.javarush.task.task33.task3309;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/*
Комментарий внутри xml
*/
public class Solution {
    public static String toXmlWithComment(Object obj, String tagName, String comment) {
        try {
        StringWriter stringWriter = new StringWriter();
        //stringWriter.write(str);

        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(obj, stringWriter);

        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        StringReader sr = new StringReader(stringWriter.toString());
        InputSource is = new InputSource(sr);
        Document document = documentBuilder.parse(is);



        NodeList elements = document.getElementsByTagName("*");
        for (int i = 0; i < elements.getLength(); i++) {
               Node element = elements.item(i);
               Comment ourComment = document.createComment(comment);
               if( element.getNodeName().equals(tagName)){
                    element.getParentNode().insertBefore(ourComment, element);
                }
               else if(element.getNodeName().equals(tagName)){

                   if(!element.getTextContent().matches(".*[<>&'\"].*") & element.getPreviousSibling().getNodeType() != Node.COMMENT_NODE) {
                       Node parent = element.getParentNode();
                       parent.insertBefore(ourComment, element);
                   }
                   else if(element.getTextContent().matches(".*[<>&'\"].*") & element.getPreviousSibling().getNodeType()!= Node.COMMENT_NODE) {
                       Node parent = element.getParentNode();
                       parent.insertBefore(ourComment, element);
                       CDATASection cdataSection = document.createCDATASection(element.getTextContent());
                       element.setTextContent("");
                       element.appendChild(cdataSection);
                   }
               }

        }

        Transformer tr = TransformerFactory.newInstance().newTransformer();
        tr.setOutputProperty(OutputKeys.STANDALONE, "no");
        tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tr.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(document);
        StringWriter stringWriter2 = new StringWriter();
        StreamResult result = new StreamResult(stringWriter2);
        tr.transform(source, result);
        return stringWriter2.toString();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
           e.printStackTrace();
        }catch (TransformerException e){
            e.printStackTrace();
        }catch(JAXBException e){
            e.printStackTrace();
        }catch (IOException e) {
           e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><first><second>some string</second>" +
                "<second>some string</second><second><![CDATA[need CDATA because of < and >]]></second><second/></first>";
        String comment = "it's a comment";
        String tagName = "second";
        //System.out.println(toXmlWithComment(null, tagName, comment, str));

    }
}
