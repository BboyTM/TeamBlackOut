package com.teamblackout.app.Packages;

import android.content.Context;
import android.os.AsyncTask;

import com.teamblackout.app.XMLParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class GetAPKList extends AsyncTask<ArrayList<App>, Void, ArrayList<App>> {
    // All static variables
    static final String BASEURL = "http://jbthemes.com/teamblackedoutapps/";
    static final String APPURL = "Updates-XML-PAID/";
    String mURL = null;
    // XML node keys
    static final String KEY_ITEM = "Element"; // parent node
    static final String KEY_NAME = "Name";
    static final String KEY_ICON = "ThumbnailUrl";
    static final String KEY_MD5 = "MD5";
    static final String KEY_DOWNLOAD = "Url";
    static final String KEY_DATE = "Date";
    static final String KEY_PREVIEW = "Preview";
    static final String KEY_DESCRIPTION = "Desc";
    static final String KEY_SECTION = "Section";
    ArrayList<App> menuItems = new ArrayList<App>();
    Context mcontext;


    public GetAPKList(String url, Context context) {
        mURL = url;
        mcontext = context;
    }

    @Override
    protected void onPreExecute() {
        //this
    }

    protected ArrayList<App> doInBackground(ArrayList<App>... passing) {
        XMLParser parser = new XMLParser(mcontext);
        String builtUrl = BASEURL + APPURL + mURL;
        String[] xml = parser.getXmlFromUrl(builtUrl); // getting XML
        Document doc = parser.getDomElement(xml[0]); // getting DOM element
        NodeList nl = doc.getElementsByTagName(KEY_ITEM);
        for (int i = 0; i < nl.getLength(); i++) {
            Element e = (Element) nl.item(i);
            App newAPK = new App(
                    parser.getValue(e, KEY_NAME),
                    parser.getValue(e, KEY_ICON),
                    parser.getValue(e, KEY_MD5),
                    parser.getValue(e, KEY_DOWNLOAD),
                    parser.getValue(e, KEY_DATE),
                    parser.getValue(e, KEY_PREVIEW),
                    parser.getValue(e, KEY_DESCRIPTION),
                    parser.getValue(e, KEY_SECTION));
            menuItems.add(newAPK);
        }
        return menuItems;
    }

    protected void onPostExecute(Void unused) {
    }
}