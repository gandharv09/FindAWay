package iiitd.ngo.findaway;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class RssParseHandler extends DefaultHandler {

	private List<Data> Datas;
	
	// Used to reference item while parsing
	final int state_unknown = 0;
    final int state_title = 1;
    final int state_location = 2;
    final int state_discount = 3;
    final int state_image = 4;
    final int state_description=5;
    final int state_index =6;
    final int state_validity=7;
    final int state_latlong =8;
    final int state_address=9;
    final int state_phone=10;
    final int state_zomato=11;
    final int state_coupon=12;
    final int state_term = 13;
    
    
    int currentState = state_unknown;
	// Parsing title indicator
	//private boolean parsingDescription;
	// Parsing link indicator
	//private boolean parsingLink;
	Data item;
	Data Members;
	Data feed;
	boolean itemFound = false;
	public RssParseHandler() {
		Datas = new ArrayList<Data>();
	}
	
	public List<Data> getItems() {
		return Datas;
	}
	
	public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        // TODO Auto-generated method stub
			//Log.d("start","start");
        if (localName.equalsIgnoreCase("item")){
            itemFound = true;
            item = new Data();
            currentState = state_unknown;
        }
        
        
        else if (localName.equalsIgnoreCase("title")){
        	//Log.d("found","title");
            currentState = state_title;
        }
        else if (localName.equalsIgnoreCase("location")){
        	 //Log.d("SNO", Thread.currentThread().getName());
            currentState = state_location;
        	//Log.d("found","location");
        }
        else if (localName.equalsIgnoreCase("discount")){
        	 //Log.d("MEMBER", Thread.currentThread().getName());
            currentState = state_discount;
        	//Log.d("found","discount");
        }
        else if (localName.equalsIgnoreCase("description")){
       	 //Log.d("MEMBER", Thread.currentThread().getName());
           currentState = state_description;
       	//Log.d("found","discount");
       }
        else if (localName.equalsIgnoreCase("logo")){
       	 //Log.d("MEMBER", Thread.currentThread().getName());
           currentState = state_image;
       	//Log.d("found","discount");
       }else if (localName.equalsIgnoreCase("indexno")){
       	 //Log.d("MEMBER", Thread.currentThread().getName());
           currentState = state_index;
       	//Log.d("found","discount");
       }
       else if (localName.equalsIgnoreCase("validtill")){
       	//Log.d("found","title");
           currentState = state_validity;
       }else if (localName.equalsIgnoreCase("extracond")){
       	//Log.d("found","title");
           currentState = state_term;
       }
       else if (localName.equalsIgnoreCase("coordinates")){
       	//Log.d("found","title");
           currentState = state_latlong;
       }
       else if (localName.equalsIgnoreCase("addresss")){
       	//Log.d("found","title");
           currentState = state_address;
       }
       else if (localName.equalsIgnoreCase("phone")){
       	//Log.d("found","title");
           currentState = state_phone;
       }
       else if (localName.equalsIgnoreCase("zomatolink")){
          	//Log.d("found","title");
              currentState = state_zomato;
          }
       else if (localName.equalsIgnoreCase("couponcode")){
         	//Log.d("found","title");
             currentState = state_coupon;
         }
        
         else{
            currentState = state_unknown;
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        // TODO Auto-generated method stub
        if (localName.equalsIgnoreCase("item")){
            Datas.add(item);
            
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        // TODO Auto-generated method stub

        String strCharacters = new String(ch,start,length);
      //  Log.d("string",strCharacters);
        if (itemFound==true){
        // "item" tag found, it's item's parameter
            switch(currentState){
           
            case state_location:
            	//Log.d("loaction",strCharacters);
                item.setLocation(strCharacters);
                break;
            case state_discount:
            	//Log.d("discount",strCharacters);
                item.setDiscount(strCharacters);
                break;
            case state_title:
            	//Log.d("title",strCharacters);
                item.setName(strCharacters);
                break;
            case state_validity:
            	//Log.d("title",strCharacters);
                item.setValidity(strCharacters);
                break;
            case state_address:
            	//Log.d("title",strCharacters);
                item.setAddress(strCharacters);
                break;
            case state_phone:
            	//Log.d("title",strCharacters);
                item.setPhone(strCharacters);
                break;
            case state_latlong:
            	//Log.d("latlong",strCharacters);
                item.setLatLong(strCharacters);
                break;
            case state_zomato:
            	//Log.d("title",strCharacters);
                item.setZomato(strCharacters);
                break;
            case state_description:
            	//Log.d("title",strCharacters);
                item.setDescription(strCharacters);
                break;
            case state_coupon:
            	//Log.d("title",strCharacters);
                item.setCoupon(strCharacters);
                break;
            case state_term:
            	//Log.d("title",strCharacters);
                item.setTerm(strCharacters);
                break;
            case state_image:
            	//Log.d("image",strCharacters);
            	String str = strCharacters.replace("\"","");
                item.setImage(str);
                break;
            case state_index:
            	//Log.d("title",strCharacters);
                item.setIndex(strCharacters);
                break;
            
              default:
                break;
            }
        }
        /*else{
        // not "item" tag found, it's feed's parameter
            switch(currentState){
            

            case state_location:
                item.setLocation(strCharacters);
                break;
            case state_discount:
                item.setDiscount(strCharacters);
                break;
            case state_title:
                item.setName(strCharacters);
                break;
                     
           
            default:
                break;
            }
        }*/

        currentState = state_unknown;
    }

}
