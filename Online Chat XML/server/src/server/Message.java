package server;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.util.List;

public class Message {
    private Document parsedXMLFile;
    private StringBuilder XMLFile = new StringBuilder();
    private int ID;

    public void getDocFromXML(byte[] message){
        try(InputStream reader = new ByteArrayInputStream(message)) {
            parsedXMLFile = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(reader);
        }catch (ParserConfigurationException | IOException | SAXException e){
            e.printStackTrace();
        }
    }

    public void getXMLFromString(List<String> blocks){
        XMLStreamWriter writer = null;
        try{
            writer = XMLOutputFactory.newInstance().createXMLStreamWriter(new OutputStreamWriter(new OutputStream() {
                @Override
                public void write(int b) {
                    XMLFile.append((char)b);
                }
            }));

            writer.writeStartDocument("1.0");
            writeSubBlock(writer, blocks);
            writer.writeEndDocument();
        }catch (XMLStreamException e){
            e.printStackTrace();
        }finally {
            if(writer != null){
                try{
                    writer.close();
                }catch (XMLStreamException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void writeSubBlock(XMLStreamWriter writer, List<String> blocks){
        if(blocks.size() == 0){
            return;
        }

        String blockName = blocks.get(0);
        try {
            int start = 1;
            writer.writeStartElement(blockName);
            if(blocks.get(1).equals("attr")){
                writer.writeAttribute(blocks.get(2), blocks.get(3));
                start = 4;
            }
            if(blocks.get(1).equals("value")){
                writer.writeCharacters(blocks.get(2));
                start = 3;
            }
            if(start < blocks.size()) {
                String subBlockName = blocks.get(start);
                for (int i = start; i < blocks.size(); ++i) {
                    if (blocks.get(i).equals("/" + subBlockName)) {
                        List<String> subBlock = blocks.subList(start, i);
                        writeSubBlock(writer, subBlock);
                        start = i + 1;
                        if(start >= blocks.size()){
                            break;
                        }
                        subBlockName = blocks.get(start);
                    }
                }
            }
            writer.writeEndElement();
        }catch (XMLStreamException e){
            e.printStackTrace();
        }
    }

    public Document getParsedXMLFile(){
        return parsedXMLFile;
    }

    public String getXMLFile(){
        return XMLFile.toString();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
