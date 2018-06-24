package cn.colintree.aix.ColinTreeListView;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.util.YailList;

@DesignerComponent(version = ColinTreeListView.VERSION,
    description = "by ColinTree at http://aix.colintree.cn/",
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    iconName = "aiwebres/elementIcon.png")
@SimpleObject(external = true)
public class ColinTreeListViewElement extends AndroidNonvisibleComponent {

    private ColinTreeListView.Element element;

    public ColinTreeListViewElement(ComponentContainer container) {
        super(container.$form());
    }

    //----------------------------Functions------------------------

    @SimpleFunction
    public void LinkToElement(ColinTreeListView listview, int elementIndex) {
        element = listview.getElement(elementIndex);
    }

    //--------------------------Element Value-------------------------

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void ElementValue(YailList value) {
        element.set(value);
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public YailList ElementValue() {
        return element.toYailList();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void IconImage(String value) {
        element.setIcon(value);
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public String IconImage() {
        return element.getIcon();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void Text(String text) {
        element.setText(text);
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public String Text() {
        return element.getText();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void SubText(String subtext) {
        element.setSubText(subtext);
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public String SubText() {
        return element.getSubText();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int Size() {
        return element.size();
    }

    //-------------------------Appearance---------------------------

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void UseGlobalProperties(boolean useGlobal) {
        element.setRefreshLock(!useGlobal);
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public boolean UseGlobalProperties() {
        return element.getRefreshLock();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int ElementBackgroundColor() {
        return element.ha.BackgroundColor();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void ElementBackgroundColor(int argb) {
        element.ha.BackgroundColor(argb);
    }

    //------------Properties that exists in ListView--------------

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int ElementHeight() {
        return element.ha.Height();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void ElementHeight(int height) {
        element.ha.Height(height);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int WidthBeforeIcon() {
        return element.labelBeforeIcon.Width();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void WidthBeforeIcon(int width) {
        element.labelBeforeIcon.Width(width);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int WidthAfterIcon() {
        return element.labelAfterIcon.Width();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void WidthAfterIcon(int width) {
        element.labelAfterIcon.Width(width);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int IconWidth() {
        return element.icon.Width();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void IconWidth(int width) {
        element.icon.Width(width);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int IconHeight() {
        return element.icon.Height();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void IconHeight(int height) {
        element.icon.Height(height);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int IconShape() {
        return element.icon.Shape();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void IconShape(int shape) {
        element.icon.Shape(shape);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int IconBackgroundColor() {
        return element.icon.BackgroundColor();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void IconBackgroundColor(int argb) {
        element.icon.BackgroundColor(argb);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int IconTextColor() {
        return element.icon.TextColor();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void IconTextColor(int argb) {
        element.icon.TextColor(argb);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public float IconTextFontSize() {
        return element.icon.FontSize();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void IconTextFontSize(float size) {
        element.icon.FontSize(size);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public boolean IconTextFontBold() {
        return element.icon.FontBold();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void IconTextFontBold(boolean bold) {
        element.icon.FontBold(bold);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int TextColor() {
        return element.size2Label.TextColor();
        // but this is also applied for size3MainText...
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void TextColor(int argb) {
        element.size2Label.TextColor(argb);
        element.size3MainText.TextColor(argb);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public float TextFontSize() {
        return element.size2Label.FontSize();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void TextFontSize(float size) {
        element.size2Label.FontSize(size);
        element.size3MainText.FontSize(size);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public boolean TextFontBold() {
        return element.size2Label.FontBold();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void TextFontBold(boolean bold) {
        element.size2Label.FontBold(bold);
        element.size3MainText.FontBold(bold);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public float TextHeight() {
        return element.size2Label.Height();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void TextHeight(int height) {
        element.size2Label.Height(height);
        element.size3MainText.Height(height);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int SubTextColor() {
        return element.size3SubText.TextColor();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void SubTextColor(int argb) {
        element.size3SubText.TextColor(argb);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public float SubTextFontSize() {
        return element.size3SubText.FontSize();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void SubTextFontSize(float size) {
        element.size3SubText.FontSize(size);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public boolean SubTextFontBold() {
        return element.size3SubText.FontBold();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void SubTextFontBold(boolean bold) {
        element.size3SubText.FontBold(bold);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public float SubTextHeight() {
        return element.size3SubText.Height();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void SubTextHeight(int height) {
        element.size3SubText.Height(height);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public float WidthBeforeExtraButton() {
        return element.labelBeforeExtraButton.Width();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void WidthBeforeExtraButton(int width) {
        element.labelBeforeExtraButton.Width(width);
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public String ExtraButtonText() {
        return element.extraButton.Text();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void ExtraButtonText(String text) {
        element.extraButton.Text(text);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public float ExtraButtonTextFontSize() {
        return element.extraButton.FontSize();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void ExtraButtonTextFontSize(float size) {
        element.extraButton.FontSize(size);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public boolean ExtraButtonTextFontBold() {
        return element.extraButton.FontBold();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void ExtraButtonTextFontBold(boolean bold) {
        element.extraButton.FontBold(bold);
    }
    
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public float ExtraButtonWidth() {
        return element.extraButton.Width();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void ExtraButtonWidth(int width) {
        element.extraButton.Width(width);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public float ExtraButtonHeight() {
        return element.extraButton.Height();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void ExtraButtonHeight(int height) {
        element.extraButton.Height(height);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int ExtraButtonBackgroundColor() {
        return element.extraButton.BackgroundColor();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void ExtraButtonBackgroundColor(int argb) {
        element.extraButton.BackgroundColor(argb);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int ExtraButtonShape() {
        return element.extraButton.Shape();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void ExtraButtonShape(int shape) {
        element.extraButton.Shape(shape);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int UnderlineColor() {
        return element.underline.BackgroundColor();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void UnderlineColor(int argb) {
        element.underline.BackgroundColor();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int UnderlineWidth() {
        return element.underline.Height();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void UnderlineWidth(int lineWidth) {
        element.underline.Height(lineWidth);
    }


    // TODO:=======================STATIC=============================

    //--------------------------Element Value-------------------------

    @SimpleFunction
    public static void ElementValue_(ColinTreeListView listview, int elementIndex, YailList value) {
        listview.getElement(elementIndex).set(value);
    }
    @SimpleFunction
    public static YailList ElementValue(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).toYailList();
    }

    @SimpleFunction
    public static void IconImage_(ColinTreeListView listview, int elementIndex, String value) {
        listview.getElement(elementIndex).setIcon(value);
    }
    @SimpleFunction
    public static String IconImage(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).getIcon();
    }

    @SimpleFunction
    public static void Text_(ColinTreeListView listview, int elementIndex, String text) {
        listview.getElement(elementIndex).setText(text);
    }
    @SimpleFunction
    public static String Text(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).getText();
    }

    @SimpleFunction
    public static void SubText_(ColinTreeListView listview, int elementIndex, String subtext) {
        listview.getElement(elementIndex).setSubText(subtext);
    }
    @SimpleFunction
    public static String SubText(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).getSubText();
    }

    @SimpleFunction
    public static int Size(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).size();
    }

    //-------------------------Appearance---------------------------

    @SimpleFunction
    public static void UseGlobalProperties_(ColinTreeListView listview, int elementIndex, boolean useGlobal) {
        listview.getElement(elementIndex).setRefreshLock(!useGlobal);
    }
    @SimpleFunction
    public static boolean UseGlobalProperties(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).getRefreshLock();
    }

    @SimpleFunction
    public static int ElementBackgroundColor(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).ha.BackgroundColor();
    }
    @SimpleFunction
    public static void ElementBackgroundColor_(ColinTreeListView listview, int elementIndex, int argb) {
        listview.getElement(elementIndex).ha.BackgroundColor(argb);
    }

    //------------Properties that exists in ListView--------------

    @SimpleFunction
    public static int ElementHeight(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).ha.Height();
    }
    @SimpleFunction
    public static void ElementHeight_(ColinTreeListView listview, int elementIndex, int height) {
        listview.getElement(elementIndex).ha.Height(height);
    }

    @SimpleFunction
    public static int WidthBeforeIcon(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).labelBeforeIcon.Width();
    }
    @SimpleFunction
    public static void WidthBeforeIcon_(ColinTreeListView listview, int elementIndex, int width) {
        listview.getElement(elementIndex).labelBeforeIcon.Width(width);
    }

    @SimpleFunction
    public static int WidthAfterIcon(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).labelAfterIcon.Width();
    }
    @SimpleFunction
    public static void WidthAfterIcon_(ColinTreeListView listview, int elementIndex, int width) {
        listview.getElement(elementIndex).labelAfterIcon.Width(width);
    }

    @SimpleFunction
    public static int IconWidth(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).icon.Width();
    }
    @SimpleFunction
    public static void IconWidth_(ColinTreeListView listview, int elementIndex, int width) {
        listview.getElement(elementIndex).icon.Width(width);
    }

    @SimpleFunction
    public static int IconHeight(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).icon.Height();
    }
    @SimpleFunction
    public static void IconHeight_(ColinTreeListView listview, int elementIndex, int height) {
        listview.getElement(elementIndex).icon.Height(height);
    }

    @SimpleFunction
    public static int IconShape(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).icon.Shape();
    }
    @SimpleFunction
    public static void IconShape_(ColinTreeListView listview, int elementIndex, int shape) {
        listview.getElement(elementIndex).icon.Shape(shape);
    }

    @SimpleFunction
    public static int IconBackgroundColor(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).icon.BackgroundColor();
    }
    @SimpleFunction
    public static void IconBackgroundColor_(ColinTreeListView listview, int elementIndex, int argb) {
        listview.getElement(elementIndex).icon.BackgroundColor(argb);
    }

    @SimpleFunction
    public static int IconTextColor(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).icon.TextColor();
    }
    @SimpleFunction
    public static void IconTextColor_(ColinTreeListView listview, int elementIndex, int argb) {
        listview.getElement(elementIndex).icon.TextColor(argb);
    }

    @SimpleFunction
    public static float IconTextFontSize(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).icon.FontSize();
    }
    @SimpleFunction
    public static void IconTextFontSize_(ColinTreeListView listview, int elementIndex, float size) {
        listview.getElement(elementIndex).icon.FontSize(size);
    }

    @SimpleFunction
    public static boolean IconTextFontBold(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).icon.FontBold();
    }
    @SimpleFunction
    public static void IconTextFontBold_(ColinTreeListView listview, int elementIndex, boolean bold) {
        listview.getElement(elementIndex).icon.FontBold(bold);
    }

    @SimpleFunction
    public static int TextColor(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).size2Label.TextColor();
        // but this is also applied for size3MainText...
    }
    @SimpleFunction
    public static void TextColor_(ColinTreeListView listview, int elementIndex, int argb) {
        listview.getElement(elementIndex).size2Label.TextColor(argb);
        listview.getElement(elementIndex).size3MainText.TextColor(argb);
    }

    @SimpleFunction
    public static float TextFontSize(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).size2Label.FontSize();
    }
    @SimpleFunction
    public static void TextFontSize_(ColinTreeListView listview, int elementIndex, float size) {
        listview.getElement(elementIndex).size2Label.FontSize(size);
        listview.getElement(elementIndex).size3MainText.FontSize(size);
    }

    @SimpleFunction
    public static boolean TextFontBold(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).size2Label.FontBold();
    }
    @SimpleFunction
    public static void TextFontBold_(ColinTreeListView listview, int elementIndex, boolean bold) {
        listview.getElement(elementIndex).size2Label.FontBold(bold);
        listview.getElement(elementIndex).size3MainText.FontBold(bold);
    }

    @SimpleFunction
    public static float TextHeight(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).size2Label.Height();
    }
    @SimpleFunction
    public static void TextHeight_(ColinTreeListView listview, int elementIndex, int height) {
        listview.getElement(elementIndex).size2Label.Height(height);
        listview.getElement(elementIndex).size3MainText.Height(height);
    }

    @SimpleFunction
    public static int SubTextColor(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).size3SubText.TextColor();
    }
    @SimpleFunction
    public static void SubTextColor_(ColinTreeListView listview, int elementIndex, int argb) {
        listview.getElement(elementIndex).size3SubText.TextColor(argb);
    }

    @SimpleFunction
    public static float SubTextFontSize(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).size3SubText.FontSize();
    }
    @SimpleFunction
    public static void SubTextFontSize_(ColinTreeListView listview, int elementIndex, float size) {
        listview.getElement(elementIndex).size3SubText.FontSize(size);
    }

    @SimpleFunction
    public static boolean SubTextFontBold(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).size3SubText.FontBold();
    }
    @SimpleFunction
    public static void SubTextFontBold_(ColinTreeListView listview, int elementIndex, boolean bold) {
        listview.getElement(elementIndex).size3SubText.FontBold(bold);
    }

    @SimpleFunction
    public static float SubTextHeight(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).size3SubText.Height();
    }
    @SimpleFunction
    public static void SubTextHeight_(ColinTreeListView listview, int elementIndex, int height) {
        listview.getElement(elementIndex).size3SubText.Height(height);
    }

    @SimpleFunction
    public static float WidthBeforeExtraButton(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).labelBeforeExtraButton.Width();
    }
    @SimpleFunction
    public static void WidthBeforeExtraButton_(ColinTreeListView listview, int elementIndex, int width) {
        listview.getElement(elementIndex).labelBeforeExtraButton.Width(width);
    }
    @SimpleFunction
    public static String ExtraButtonText(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).extraButton.Text();
    }
    @SimpleFunction
    public static void ExtraButtonText_(ColinTreeListView listview, int elementIndex, String text) {
        listview.getElement(elementIndex).extraButton.Text(text);
    }

    @SimpleFunction
    public static float ExtraButtonTextFontSize(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).extraButton.FontSize();
    }
    @SimpleFunction
    public static void ExtraButtonTextFontSize_(ColinTreeListView listview, int elementIndex, float size) {
        listview.getElement(elementIndex).extraButton.FontSize(size);
    }

    @SimpleFunction
    public static boolean ExtraButtonTextFontBold(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).extraButton.FontBold();
    }
    @SimpleFunction
    public static void ExtraButtonTextFontBold_(ColinTreeListView listview, int elementIndex, boolean bold) {
        listview.getElement(elementIndex).extraButton.FontBold(bold);
    }
    
    @SimpleFunction
    public static float ExtraButtonWidth(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).extraButton.Width();
    }
    @SimpleFunction
    public static void ExtraButtonWidth_(ColinTreeListView listview, int elementIndex, int width) {
        listview.getElement(elementIndex).extraButton.Width(width);
    }

    @SimpleFunction
    public static float ExtraButtonHeight(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).extraButton.Height();
    }
    @SimpleFunction
    public static void ExtraButtonHeight_(ColinTreeListView listview, int elementIndex, int height) {
        listview.getElement(elementIndex).extraButton.Height(height);
    }

    @SimpleFunction
    public static int ExtraButtonBackgroundColor(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).extraButton.BackgroundColor();
    }
    @SimpleFunction
    public static void ExtraButtonBackgroundColor_(ColinTreeListView listview, int elementIndex, int argb) {
        listview.getElement(elementIndex).extraButton.BackgroundColor(argb);
    }

    @SimpleFunction
    public static int ExtraButtonShape(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).extraButton.Shape();
    }
    @SimpleFunction
    public static void ExtraButtonShape_(ColinTreeListView listview, int elementIndex, int shape) {
        listview.getElement(elementIndex).extraButton.Shape(shape);
    }

    @SimpleFunction
    public static int UnderlineColor(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).underline.BackgroundColor();
    }
    @SimpleFunction
    public static void UnderlineColor_(ColinTreeListView listview, int elementIndex, int argb) {
        listview.getElement(elementIndex).underline.BackgroundColor();
    }

    @SimpleFunction
    public static int UnderlineWidth(ColinTreeListView listview, int elementIndex) {
        return listview.getElement(elementIndex).underline.Height();
    }
    @SimpleFunction
    public static void UnderlineWidth_(ColinTreeListView listview, int elementIndex, int lineWidth) {
        listview.getElement(elementIndex).underline.Height(lineWidth);
    }

}