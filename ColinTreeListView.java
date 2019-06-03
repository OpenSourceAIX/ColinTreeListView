package cn.colintree.aix.ColinTreeListView;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.ComponentConstants;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.ButtonBase;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HVArrangement;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.VerticalArrangement;
import com.google.appinventor.components.runtime.VerticalScrollArrangement;
import com.google.appinventor.components.runtime.util.AsyncCallbackPair;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.ViewUtil;
import com.google.appinventor.components.runtime.util.YailList;

import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.ScrollView;
import android.view.MotionEvent;

@DesignerComponent(version = ColinTreeListView.VERSION,
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    iconName = "aiwebres/icon.png",
    helpUrl = "http://aix.colintree.cn/en/extensions/ColinTreeListView.html")

@SimpleObject(external = true)
@UsesPermissions(permissionNames = "android.permission.INTERNET," +
    "android.permission.ACCESS_NETWORK_STATE," +
    "android.permission.READ_EXTERNAL_STORAGE")
public class ColinTreeListView extends AndroidNonvisibleComponent implements Component {

    // VERSION 2:
    //   First version to be released
    // VERSION 3:
    //   Adapted to new Appinventor (since 2017.12.27, Companion 2.45) (Fixed NoSuchMethodError)
    //   Added properties related to image loading - AsyncImageLoad & CacheImage (that in a same path)
    //   Added property - ScrollBottomAfterAdd
    // VERSION 4:
    //   Adapted to all platforms (ai2.appinventor.mit.edu , thunkable , etc.) 
    //     There should not more NoSuchMethodError at anywhere.
    // VERSION 5:
    //   Added icon text related properties
    //   Added lastClickedElement & lastClickedIcon
    // VERSION 6:
    //   Added properties of text height (both main- & sub-text)
    // VERSION 7:
    //   Fixed that lastClickedElement starts from 0
    //   Fixed that images that cached by a same path would act wired when one of them is clicked
    //   Added a extra button
    // VERSION 8:
    //   Fixed extraButtonEnabled not implemented
    //   Added support of direct(static) handlers in ColinTreeListViewElement
    // VERSION 9:
    //   Fixed Error of Visiblility
    //   Added ClearCache() & ClearAllCache() -- still in test, it should works
    // VERSION 10:
    //   Added Get by @10MINT 
    //   Added LastLongClickedElement by @10MINT 
    //   Fixes Label cannot click on some platforms
    // VERSION 11:
    //   Added Translations (need platform supporting)
    //   Fixed a Get method bug (#11)
    //   Added ExtraButtonImage in ColinTreeListViewElement
    //   Removed flag deprecated from all blocks
    public static final int VERSION = 11;

    private static final String LOG_TAG = "ColinTreeListView";

    private final ArrayList<Element> elementList = new ArrayList<Element>();
    private HVArrangement vaContainer = null;
    private int currentListSize = 0;

    private final Handler handler = new Handler();

    private int lastClickedElement = 0;
    private int lastLongClickedElement = 0;
    private int lastClickedIcon = 0;
    private int lastClickedExtraButton = 0;

    // Appearance
    private int elementHeight = 57;
    private int elementTouchDownColor = COLOR_DEFAULT;
    private int elementWidthBeforeIcon = 7;
    private int elementWidthAfterIcon = 5;
    private int elementIconWidth = 40;
    private int elementIconHeight = 40;
    private int elementIconShape = BUTTON_SHAPE_ROUNDED;
    private int elementIconBgColor = COLOR_DEFAULT;
    private int elementIconTextColor = COLOR_DEFAULT;
    private float elementIconTextFontSize = 24;
    private boolean elementIconTextFontBold = true;
    private boolean elementIconMultiParams = false;
    private int elementIconPaddings = 0;
    private int elementTextColor = COLOR_BLACK;
    private float elementTextFontSize = 14f;
    private boolean elementTextFontBold = false;
    private int elementTextHeight = 18;
    private int elementSubTextColor = COLOR_LTGRAY;
    private float elementSubTextFontSize = 12;
    private boolean elementSubTextFontBold = false;
    private int elementSubTextHeight = 18;
    private int elementWidthBeforeExtraButton = 2;
    private String elementExtraButtonText = "";
    private float elementExtraButtonTextFontSize = 12;
    private boolean elementExtraButtonTextFontBold = false;
    private int elementExtraButtonWidth = 20;
    private int elementExtraButtonHeight = 20;
    private int elementExtraButtonPaddings = 0;
    private int elementExtraButtonBgColor = COLOR_LTGRAY;
    private int elementExtraButtonShape = BUTTON_SHAPE_OVAL;
    private String elementExtraButtonImage = "";
    private int elementUnderlineColor = COLOR_LTGRAY;
    private int elementUnderlineWidth = 1;

    // Behaviour
    private boolean scrollBottomAfterAdd = false;
    private boolean asyncImageLoad = false;
    private boolean cacheImage = false;
    private boolean extraButtonEnabled = false;

    private HashMap<String, CachedImage> iconMap = new HashMap<String, CachedImage>();

    private final Form form;


    private static final YailList makeYailList(Object... obj) {
        return YailList.makeList(obj);
    }


    public ColinTreeListView(ComponentContainer container) {
        super(container.$form());
        form = container.$form();
        Log.d(LOG_TAG, "ColinTreeListView Created");
    }

    @SimpleFunction
    public void Initialize(VerticalArrangement verticalArrangement) {
        vaContainer = verticalArrangement;
        vaContainer.AlignHorizontal(ComponentConstants.GRAVITY_CENTER_HORIZONTAL);
    }
    @SimpleFunction
    public void Initialize_Scroll(VerticalScrollArrangement verticalScrollArrangement) {
        vaContainer = verticalScrollArrangement;
        vaContainer.AlignHorizontal(ComponentConstants.GRAVITY_CENTER_HORIZONTAL);
    }

    @SimpleFunction
    public void Clear() {
        Set(YailList.makeEmptyList());
    }

    @SimpleFunction
    public void Set(YailList list) {
        int size = list.size();
        Object sublistElement;
        // Set the new list elements
        for (int i = 0; i < size; i++) {
            sublistElement = list.getObject(i);
            if (sublistElement instanceof YailList) {
                if (currentListSize > i) {
                    SetElement(i+1, (YailList)sublistElement);
                    // Calling the function that is shown in bky, so the index start from 1
                } else {
                    AddElement((YailList)sublistElement);
                }
            } else {
                if (currentListSize > i) {
                    SetElement(i+1, makeYailList(sublistElement));
                    // Calling the function that is shown in bky, so the index start from 1
                } else {
                    AddElement(makeYailList(sublistElement));
                }
            }
        }
        // Hide the elements that is created but not uesd
        for (int i = list.size(); i < currentListSize; i++) {
            getElement(i + 1).hide();
        }
        currentListSize = list.size();
    }

    //added by @10MINT
    //returns the list of the listview
    @SimpleFunction
    public YailList Get() {
        ArrayList<YailList> stringRepresentation = new ArrayList<YailList>();
        for (int i = 1; i <= this.currentListSize; i++) {
            stringRepresentation.add(GetElement(i));
        }
        return YailList.makeList(stringRepresentation);
    }

    @SimpleFunction
    public void AddElement(YailList element) {
        int elementListSize = elementList.size();
        if (currentListSize < elementListSize && elementListSize > 0) {
            getElement(currentListSize + 1)
                .show()
                .set(element);
        } else {
            final int elementIndex = currentListSize;
            elementList.add(new Element(vaContainer, element) {
                @Override
                public void onElementClick() {
                    ElementClick(elementIndex);
                }
                @Override
                public boolean onElementLongClick() {
                    return ElementLongClick(elementIndex);
                }
                @Override
                public void onElementTouchDown() {
                    ElementTouchDown(elementIndex);
                }
                @Override
                public void onElementTouchUp() {
                    ElementTouchUp(elementIndex);
                }

                @Override
                public void onIconClick() {
                    IconClick(elementIndex);
                }
                @Override
                public boolean onIconLongClick() {
                    return IconLongClick(elementIndex);
                }
                @Override
                public void onIconTouchDown() {
                    IconTouchDown(elementIndex);
                }
                @Override
                public void onIconTouchUp() {
                    IconTouchUp(elementIndex);
                }

                @Override
                public void onExtraButtonClick() {
                    ExtraButtonClick(elementIndex);
                }
                @Override
                public boolean onExtraButtonLongClick() {
                    return ExtraButtonLongClick(elementIndex);
                }
                @Override
                public void onExtraButtonTouchDown() {
                    ExtraButtonTouchDown(elementIndex);
                }
                @Override
                public void onExtraButtonTouchUp() {
                    ExtraButtonTouchUp(elementIndex);
                }
            });
        }
        currentListSize++;

        if (scrollBottomAfterAdd && (vaContainer.getView() instanceof ScrollView)) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((ScrollView)vaContainer.getView()).fullScroll(ScrollView.FOCUS_DOWN);
                }
            }, 20);
        }
    }
    @SimpleFunction
    public void AddEmptyElement() {
        AddElement(YailList.makeEmptyList());
    }

    private void checkIndex(int elementIndex) throws IndexOutOfBoundsException {
        if (elementIndex < 1 || elementIndex > currentListSize) {
            throw new IndexOutOfBoundsException();
        }

    }
    @SimpleFunction
    public void SetElement(int elementIndex, YailList element) {
        checkIndex(elementIndex);
        getElement(elementIndex).show().set(element);
    }
    @SimpleFunction
    public void SetElementText(int elementIndex, String text) {
        checkIndex(elementIndex);
        getElement(elementIndex).setText(text);
    }
    @SimpleFunction
    public void SetElementMainText(int elementIndex, String mainText) {
        checkIndex(elementIndex);
        getElement(elementIndex).setMainText(mainText);
    }
    @SimpleFunction
    public void SetElementSubText(int elementIndex, String subText) {
        checkIndex(elementIndex);
        getElement(elementIndex).setSubText(subText);
    }
    @SimpleFunction
    public void SetElementIcon(int elementIndex, String path) {
        checkIndex(elementIndex);
        getElement(elementIndex).setIcon(path);
    }
    @SimpleFunction
    public YailList GetElement(int elementIndex) {
        return getElement(elementIndex).toYailList();
    }

    public Element getElement(int elementIndex) {
        return elementList.get(elementIndex-1);
    }

    @SimpleFunction
    public void RemoveElement(int elementIndex) {
        checkIndex(elementIndex);
        for (int i = elementIndex-1; i < currentListSize-1; i++) {
            copyElement(i+1, i);
        }
        getElement(currentListSize).hide();
        currentListSize--;
    }
    private void copyElement(int indexFrom, int indexTo) {
        SetElement(
            indexTo+1,
            makeYailList(GetElement(indexFrom + 1).toArray())
            // Trun into object[] first, avoiding object confusing. (means that make a new object)
        );
    }


    @SimpleFunction
    public void ClearAllCache() {
        for (HashMap.Entry<String, CachedImage> entry : iconMap.entrySet()) {
            entry.getValue().releaseMemory();
            iconMap.remove(entry.getKey());
        }
        System.gc();
    }

    @SimpleFunction
    public void ClearCache(String path) {
        CachedImage ci = iconMap.get(path);
        if (ci != null) {
            ci.releaseMemory();
            iconMap.remove(path);
        }
        System.gc();
    }


    @SimpleEvent
    public void ElementClick(int elementIndex) {
        lastClickedElement = elementIndex + 1;
        EventDispatcher.dispatchEvent(this, "ElementClick", elementIndex + 1);
    }
    @SimpleEvent
    public boolean ElementLongClick(int elementIndex) {
        lastLongClickedElement = elementIndex + 1;
        return EventDispatcher.dispatchEvent(this, "ElementLongClick", elementIndex + 1);
    }
    @SimpleEvent
    public boolean ElementTouchDown(int elementIndex) {
        return EventDispatcher.dispatchEvent(this, "ElementTouchDown", elementIndex + 1);
    }
    @SimpleEvent
    public boolean ElementTouchUp(int elementIndex) {
        return EventDispatcher.dispatchEvent(this, "ElementTouchUp", elementIndex + 1);
    }

    @SimpleEvent
    public void IconClick(int elementIndex) {
        lastClickedIcon = elementIndex + 1;
        EventDispatcher.dispatchEvent(this, "IconClick", elementIndex + 1);
    }
    @SimpleEvent
    public boolean IconLongClick(int elementIndex) {
        return EventDispatcher.dispatchEvent(this, "IconLongClick", elementIndex + 1);
    }
    @SimpleEvent
    public void IconTouchDown(int elementIndex) {
        EventDispatcher.dispatchEvent(this, "IconTouchDown", elementIndex + 1);
    }
    @SimpleEvent
    public void IconTouchUp(int elementIndex) {
        EventDispatcher.dispatchEvent(this, "IconTouchUp", elementIndex + 1);
    }

    @SimpleEvent
    public void ExtraButtonClick(int elementIndex) {
        lastClickedExtraButton = elementIndex + 1;
        EventDispatcher.dispatchEvent(this, "ExtraButtonClick", elementIndex + 1);
    }
    @SimpleEvent
    public boolean ExtraButtonLongClick(int elementIndex) {
        return EventDispatcher.dispatchEvent(this, "ExtraButtonLongClick", elementIndex + 1);
    }
    @SimpleEvent
    public void ExtraButtonTouchDown(int elementIndex) {
        EventDispatcher.dispatchEvent(this, "ExtraButtonTouchDown", elementIndex + 1);
    }
    @SimpleEvent
    public void ExtraButtonTouchUp(int elementIndex) {
        EventDispatcher.dispatchEvent(this, "ExtraButtonTouchUp", elementIndex + 1);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public int LastClickedElement() {
        return lastClickedElement;
    }
    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public int LastLongClickedElement() {
        return lastLongClickedElement;
    }
    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public int LastClickedIcon() {
        return lastClickedIcon;
    }
    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public int LastClickedExtraButton() {
        return lastClickedExtraButton;
    }




    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int ElementHeight() {
        return elementHeight;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "57")
    public void ElementHeight(int height) {
        elementHeight = height;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int TouchDownColor() {
        return elementTouchDownColor;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, 
        defaultValue = DEFAULT_VALUE_COLOR_DEFAULT)
    public void TouchDownColor(int argb) {
        elementTouchDownColor = argb;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int WidthBeforeIcon() {
        return elementWidthBeforeIcon;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "7")
    public void WidthBeforeIcon(int width) {
        elementWidthBeforeIcon = width;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int WidthAfterIcon() {
        return elementWidthAfterIcon;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "5")
    public void WidthAfterIcon(int width) {
        elementWidthAfterIcon = width;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int IconWidth() {
        return elementIconWidth;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "40")
    public void IconWidth(int width) {
        elementIconWidth = width;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int IconHeight() {
        return elementIconHeight;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "40")
    public void IconHeight(int height) {
        elementIconHeight = height;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int IconShape() {
        return elementIconShape;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BUTTON_SHAPE, 
        defaultValue = ""+BUTTON_SHAPE_ROUNDED)
    public void IconShape(int shape) {
        elementIconShape = shape;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int IconBgColor() {
        return elementIconBgColor;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, 
        defaultValue = DEFAULT_VALUE_COLOR_DEFAULT)
    public void IconBgColor(int argb) {
        elementIconBgColor = argb;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int IconTextColor() {
        return elementIconTextColor;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, 
        defaultValue = DEFAULT_VALUE_COLOR_DEFAULT)
    public void IconTextColor(int argb) {
        elementIconTextColor = argb;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public float IconTextFontSize() {
        return elementIconTextFontSize;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_FLOAT, defaultValue = "24")
    public void IconTextFontSize(float size) {
        elementIconTextFontSize = size;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public boolean IconTextFontBold() {
        return elementIconTextFontBold;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "True")
    public void IconTextFontBold(boolean bold) {
        elementIconTextFontBold = bold;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public boolean IconMultiParams() {
        return elementIconMultiParams;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "False")
    public void IconMultiParams(boolean multiParams) {
        elementIconMultiParams = multiParams;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int IconPaddings() {
        return elementIconPaddings;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "0")
    public void IconPaddings(int paddings) {
        elementIconPaddings = Math.round(paddings * form.deviceDensity());
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int TextColor() {
        return elementTextColor;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR,
        defaultValue = DEFAULT_VALUE_COLOR_BLACK)
    public void TextColor(int argb) {
        elementTextColor = argb;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public float TextFontSize() {
        return elementTextFontSize;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_FLOAT, defaultValue = "14")
    public void TextFontSize(float size) {
        elementTextFontSize = size;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public boolean TextFontBold() {
        return elementTextFontBold;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "False")
    public void TextFontBold(boolean bold) {
        elementTextFontBold = bold;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public float TextHeight() {
        return elementTextHeight;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "18")
    public void TextHeight(int height) {
        elementTextHeight = height;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int SubTextColor() {
        return elementSubTextColor;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, 
        defaultValue = DEFAULT_VALUE_COLOR_LTGRAY)
    public void SubTextColor(int argb) {
        elementSubTextColor = argb;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public float SubTextFontSize() {
        return elementSubTextFontSize;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_FLOAT, defaultValue = "12")
    public void SubTextFontSize(float size) {
        elementSubTextFontSize = size;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public boolean SubTextFontBold() {
        return elementSubTextFontBold;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "False")
    public void SubTextFontBold(boolean bold) {
        elementSubTextFontBold = bold;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public float SubTextHeight() {
        return elementSubTextHeight;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "18")
    public void SubTextHeight(int height) {
        elementSubTextHeight = height;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public float WidthBeforeExtraButton() {
        return elementWidthBeforeExtraButton;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "2")
    public void WidthBeforeExtraButton(int width) {
        elementWidthBeforeExtraButton = width;
        refreshElementProperties();
    }
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public String ExtraButtonText() {
        return elementExtraButtonText;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_TEXT, defaultValue = "")
    public void ExtraButtonText(String text) {
        elementExtraButtonText = text;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public float ExtraButtonTextFontSize() {
        return elementExtraButtonTextFontSize;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_FLOAT, defaultValue = "12")
    public void ExtraButtonTextFontSize(float size) {
        elementExtraButtonTextFontSize = size;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public boolean ExtraButtonTextFontBold() {
        return elementExtraButtonTextFontBold;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "False")
    public void ExtraButtonTextFontBold(boolean bold) {
        elementExtraButtonTextFontBold = bold;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public float ExtraButtonWidth() {
        return elementExtraButtonWidth;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "20")
    public void ExtraButtonWidth(int width) {
        elementExtraButtonWidth = width;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public float ExtraButtonHeight() {
        return elementExtraButtonHeight;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "20")
    public void ExtraButtonHeight(int height) {
        elementExtraButtonHeight = height;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public float ExtraButtonPaddings() {
        return elementExtraButtonPaddings;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "0")
    public void ExtraButtonPaddings(int paddings) {
        elementExtraButtonPaddings = paddings;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int ExtraButtonBgColor() {
        return elementExtraButtonBgColor;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, 
        defaultValue = DEFAULT_VALUE_COLOR_LTGRAY)
    public void ExtraButtonBgColor(int argb) {
        elementExtraButtonBgColor = argb;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int ExtraButtonShape() {
        return elementExtraButtonShape;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BUTTON_SHAPE, 
        defaultValue = ""+BUTTON_SHAPE_OVAL)
    public void ExtraButtonShape(int shape) {
        elementExtraButtonShape = shape;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public String ExtraButtonImage() {
        return elementExtraButtonImage;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_ASSET, defaultValue = "")
    public void ExtraButtonImage(String path) {
        elementExtraButtonImage = path;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int UnderlineColor() {
        return elementUnderlineColor;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, 
        defaultValue = DEFAULT_VALUE_COLOR_LTGRAY)
    public void UnderlineColor(int argb) {
        elementUnderlineColor = argb;
        refreshElementProperties();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int UnderlineWidth() {
        return elementUnderlineWidth;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "1")
    public void UnderlineWidth(int lineWidth) {
        elementUnderlineWidth = lineWidth;
        refreshElementProperties();
    }

    private void refreshElementProperties() {
        for (int i = 0; i < currentListSize; i++) {
            getElement(i + 1).refreshProperties();
        }
    }


    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean ScrollBottomAfterAdd() {
        return scrollBottomAfterAdd;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "False")
    public void ScrollBottomAfterAdd(boolean scroll) {
        scrollBottomAfterAdd = scroll;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean AsyncImageLoad() {
        return asyncImageLoad;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "False")
    public void AsyncImageLoad(boolean async) {
        asyncImageLoad = async;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean CacheImage() {
        return cacheImage;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "False")
    public void CacheImage(boolean cache) {
        cacheImage = cache;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean ExtraButtonEnabled() {
        return extraButtonEnabled;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "False")
    public void ExtraButtonEnabled(boolean enable) {
        extraButtonEnabled = enable;
    }



    abstract class Element implements OnClickListener, OnLongClickListener, OnTouchListener {

        private ComponentContainer container;

        public static final String NOTICE = "Welcome to improve this extension with plugins :P";
        public final HorizontalArrangement ha;
        public final Label labelBeforeIcon;
        public final ButtonBase icon;
        public final Label labelAfterIcon;
        public final Label size2Label;
        public final VerticalArrangement size3Va;
        public final Label size3MainText;
        public final Label size3SubText;
        public final Label labelBeforeExtraButton;
        public final ButtonBase extraButton;
        public final Label labelAfterText;
        public final Label underline;

        private String iconValue = "";
        private String extraImagePath = "";

        private int currentSize;

        private boolean refreshLock = false;

        public Element(ComponentContainer container, YailList list) {
            this.container = container;

            ha = new HorizontalArrangement(container);
            ha.getView().setOnClickListener(this);
            ha.getView().setOnLongClickListener(this);
            ha.getView().setOnTouchListener(this);
            ha.AlignVertical(ComponentConstants.GRAVITY_CENTER_VERTICAL);
            ha.Width(LENGTH_FILL_PARENT);

            labelBeforeIcon = new Label(ha);
            labelBeforeIcon.Text("");
            labelBeforeIcon.getView().setOnClickListener(this);
            labelBeforeIcon.getView().setOnLongClickListener(this);
            labelBeforeIcon.getView().setOnTouchListener(this);

            icon = new ButtonBase(ha) {
                @Override
                public void click() {
                    onIconClick();
                }
                @Override
                public boolean longClick() {
                    return onIconLongClick();
                }
                @Override
                public void TouchDown() {
                    onIconTouchDown();
                }
                @Override
                public void TouchUp() {
                    onIconTouchUp();
                }
            };

            labelAfterIcon = new Label(ha);
            labelAfterIcon.Text("");
            labelAfterIcon.getView().setOnClickListener(this);
            labelAfterIcon.getView().setOnLongClickListener(this);
            labelAfterIcon.getView().setOnTouchListener(this);

            size2Label = new Label(ha);
            size2Label.Width(LENGTH_FILL_PARENT);
            size2Label.Text("Element Text");
            size2Label.TextAlignment(ALIGNMENT_NORMAL);
            size2Label.BackgroundColor(COLOR_NONE);
            size2Label.getView().setOnClickListener(this);
            size2Label.getView().setOnLongClickListener(this);
            size2Label.getView().setOnTouchListener(this);

            size3Va = new VerticalArrangement(ha);
            size3Va.AlignVertical(ComponentConstants.GRAVITY_CENTER_VERTICAL);
            size3Va.AlignHorizontal(ComponentConstants.GRAVITY_LEFT);
            size3Va.Width(LENGTH_FILL_PARENT);

            size3MainText = new Label(size3Va);
            size3MainText.Text("Element Main Text");
            size3MainText.TextAlignment(ALIGNMENT_NORMAL);
            size3MainText.getView().setOnClickListener(this);
            size3MainText.getView().setOnLongClickListener(this);
            size3MainText.getView().setOnTouchListener(this);

            size3SubText = new Label(size3Va);
            size3SubText.Text("Element Sub Text");
            size3SubText.TextAlignment(ALIGNMENT_NORMAL);
            size3SubText.getView().setOnClickListener(this);
            size3SubText.getView().setOnLongClickListener(this);
            size3SubText.getView().setOnTouchListener(this);

            labelBeforeExtraButton = new Label(ha);
            labelBeforeExtraButton.Text("");
            labelBeforeExtraButton.Width(2);
            labelBeforeExtraButton.getView().setOnClickListener(this);
            labelBeforeExtraButton.getView().setOnLongClickListener(this);
            labelBeforeExtraButton.getView().setOnTouchListener(this);

            extraButton = new ButtonBase(ha) {
                @Override
                public void click() {
                    onExtraButtonClick();
                }
                @Override
                public boolean longClick() {
                    return onExtraButtonLongClick();
                }
                @Override
                public void TouchDown() {
                    onExtraButtonTouchDown();
                }
                @Override
                public void TouchUp() {
                    onExtraButtonTouchUp();
                }
            };

            labelAfterText = new Label(ha);
            labelAfterText.Text("");
            labelAfterText.Width(10);
            labelAfterText.getView().setOnClickListener(this);
            labelAfterText.getView().setOnLongClickListener(this);
            labelAfterText.getView().setOnTouchListener(this);

            underline = new Label(container);
            underline.Width(LENGTH_FILL_PARENT);
            underline.Text("");
            underline.HasMargins(false);
            underline.getView().setOnClickListener(this);
            underline.getView().setOnLongClickListener(this);
            underline.getView().setOnTouchListener(this);
            refreshProperties();

            currentSize = 0;
            set(list);
        }

        // Override these to implement the events
        public abstract void onElementClick();
        public abstract boolean onElementLongClick();
        public abstract void onElementTouchDown();
        public abstract void onElementTouchUp();

        public abstract void onIconClick();
        public abstract boolean onIconLongClick();
        public abstract void onIconTouchDown();
        public abstract void onIconTouchUp();

        public abstract void onExtraButtonClick();
        public abstract boolean onExtraButtonLongClick();
        public abstract void onExtraButtonTouchDown();
        public abstract void onExtraButtonTouchUp();

        @Override
        public void onClick(View v) {
            onElementClick();
        }
        @Override
        public boolean onLongClick(View v) {
            return onElementLongClick();
        }
        private void ElementTouchDown() {
            ha.BackgroundColor(elementTouchDownColor);
            onElementTouchDown();
        }
        private void ElementTouchUp() {
            ha.BackgroundColor(0x00FFFFFF);
            onElementTouchUp();
        }
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_DOWN) {
                ElementTouchDown();
            } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                ElementTouchUp();
            }
            return false;
        }

        public Element refreshProperties() {
            if (refreshLock) {
                return this;
            }

            show();

            ha.Height(elementHeight);

            labelBeforeIcon.Width(elementWidthBeforeIcon);

            icon.Height(elementIconHeight);
            icon.Width(elementIconWidth);
            icon.Shape(elementIconShape);
            icon.BackgroundColor(elementIconBgColor);
            icon.TextColor(elementIconTextColor);
            icon.FontSize(elementIconTextFontSize);
            icon.FontBold(elementIconTextFontBold);
            ((android.widget.Button)icon.getView())
                .setPadding(elementIconPaddings, elementIconPaddings, 
                    elementIconPaddings, elementIconPaddings);
            setIcon(iconValue); // called due to elementIconMultiParams

            labelAfterIcon.Width(elementWidthAfterIcon);

            size2Label.TextColor(elementTextColor);
            size2Label.FontSize(elementTextFontSize);
            size2Label.FontBold(elementTextFontBold);

            size3MainText.TextColor(elementTextColor);
            size3MainText.FontSize(elementTextFontSize);
            size3MainText.FontBold(elementTextFontBold);
            size3MainText.Height(elementTextHeight);

            size3SubText.TextColor(elementSubTextColor);
            size3SubText.FontSize(elementSubTextFontSize);
            size3SubText.FontBold(elementSubTextFontBold);
            size3SubText.Height(elementSubTextHeight);

            labelBeforeExtraButton.Width(elementWidthBeforeExtraButton);

            Visible(extraButton, extraButtonEnabled);
            extraButton.Text(elementExtraButtonText);
            extraButton.FontSize(elementExtraButtonTextFontSize);
            extraButton.FontBold(elementExtraButtonTextFontBold);
            extraButton.Width(elementExtraButtonWidth);
            extraButton.Height(elementExtraButtonHeight);
            extraButton.BackgroundColor(elementExtraButtonBgColor);
            extraButton.Shape(elementExtraButtonShape);
            ((android.widget.Button)extraButton.getView())
                .setPadding(elementExtraButtonPaddings, elementExtraButtonPaddings, 
                    elementExtraButtonPaddings, elementExtraButtonPaddings);
            setExtraButton();

            underline.BackgroundColor(elementUnderlineColor);
            underline.Height(elementUnderlineWidth);

            return this;
        }

        public Element setRefreshLock(boolean lock) {
            this.refreshLock = lock;
            return this;
        }
        public boolean getRefreshLock() {
            return refreshLock;
        }

        public Element show() {
            Visible(ha, true);
            Visible(underline, true);
            return this;
        }
        public Element hide() {
            Visible(ha, false);
            Visible(underline, false);
            return this;
        }

        public int size() {
            return currentSize;
        }

        public Element setIcon(String value) {
            this.iconValue = value;
            String path = new String(value);
            String text = "";
            if (elementIconMultiParams && value.contains("||")) {
                String[] s = value.split("\\|\\|", 2);
                path = s[0];
                text = s[1];
            }
            icon.Text(text);
            if (size() > 1) {
                if (cacheImage) { // If cache image
                    setImageThroughCache(icon, path);
                } else { // Not cache image
                    if (asyncImageLoad) {
                        MediaUtil.getBitmapDrawableAsync(form, path, new AsyncCallbackPair<BitmapDrawable>() {
                            @Override
                            public void onFailure(String message) {}

                            @Override
                            public void onSuccess(final BitmapDrawable result) {
                                container.$context().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ViewUtil.setBackgroundDrawable(icon.getView(), result);
                                    }
                                });
                            }
                        });
                    } else { // No cache and no async, use default.
                        icon.Image(path);
                    }
                }
            }
            return this;
        }
        public String getIcon() {
            return size()>1 ? iconValue : "";
        }

        public Element setExtraButton() {
            setImageThroughCache(extraButton, elementExtraButtonImage);
            return this;
        }
        public Element setExtraButtonImage(String path) {
            this.extraImagePath = path;
            setImageThroughCache(extraButton, path);
            return this;
        }
        public String getExtraButtonImage() {
            return this.extraImagePath;
        }

        private void setImageThroughCache(ButtonBase bb, String path) {
            bb.Image(""); // As preload image
            CachedImage ci = iconMap.get(path); // Cache all the time
            if (ci != null) {
                ci.setImage(bb);
            } else {
                iconMap.put(path, new CachedImage(path, bb));
            }
        }

        public Element setText(String text) {
            if (size() < 3) {
                size2Label.Text(text);
            } else {
                setMainText(text);
            }
            return this;
        }
        public String getText() {
            return (size()<3) ? size2Label.Text() : getMainText();
        }

        public Element setMainText(String text) {
            if (size() < 3) {
                setText(text);
            } else {
                size3MainText.Text(text);
            }
            return this;
        }
        public String getMainText() {
            return (size()<3) ? getText() : size3MainText.Text();
        }

        public Element setSubText(String text) {
            if (size() < 3) {
                setText(text);
            } else {
                size3SubText.Text(text);
            }
            return this;
        }
        public String getSubText() {
            return (size()<3) ? getText() : size3SubText.Text();
        }

        public Element set(YailList list) {
            int arrayLength = list.toArray().length;
            switch (arrayLength) {
                case 1:
                    toSize1(list);
                    break;
                case 2:
                    toSize2(list);
                    break;
                default:
                    if (arrayLength < 1) { // 0 only
                        throw new RuntimeException("The list for a element should not be empty!");
                    } else { // >=3
                        toSize3(list);
                    }
            }
            return this;
        }
        public Element toSize1(YailList list) { // Text only
            currentSize = 1;
            Visible(icon, false);
            Visible(size2Label, true);
            Visible(size3Va, false);

            size2Label.Text(list.getString(0));
            return this;
        }
        public Element toSize2(YailList list) { // Icon and a Text
            currentSize = 2;
            Visible(icon, true);
            Visible(size2Label, true);
            Visible(size3Va, false);

            setIcon(list.getString(0));
            size2Label.Text(list.getString(1));
            return this;
        }
        public Element toSize3(YailList list) { // Icon and two Texts
            currentSize = 3;
            Visible(icon, true);
            Visible(size2Label, false);
            Visible(size3Va, true);

            setIcon(list.getString(0));
            size3MainText.Text(list.getString(1));
            size3SubText.Text(list.getString(2));
            return this;
        }

        public YailList toYailList() {
            switch (size()) {
                case 1:
                    return makeYailList(
                        getText());
                case 2:
                    return makeYailList(
                        getIcon(),
                        getText());
                case 3:
                    return makeYailList(
                        getIcon(),
                        getMainText(),
                        getSubText());
                default:
                    return YailList.makeEmptyList();
            }
        }
    }

    private class CachedImage {

        public final String path;
        private BitmapDrawable bd = null;
        private ArrayList<ButtonBase> callback = new ArrayList<ButtonBase>();

        public CachedImage(String path, ButtonBase bb) {
            this.path = path;
            addCallback(bb);
            if (asyncImageLoad) {
                MediaUtil.getBitmapDrawableAsync(form, path, new AsyncCallbackPair<BitmapDrawable>() {
                    @Override
                    public void onFailure(String message) {
                    }

                    @Override
                    public void onSuccess(BitmapDrawable result) {
                        gotBitmapDrawable(result);
                    }
                });
            } else { // Use built-in Synchronizer to make this operation sync
                try {
                    gotBitmapDrawable(MediaUtil.getBitmapDrawable(form, path));
                } catch(IOException ok) {
                }
            }
        }

        public void setImage(ButtonBase bb) {
            if (bd != null) {
                ViewUtil.setBackgroundDrawable(bb.getView(), new BitmapDrawable(form.getResources(), bd.getBitmap()));
            } else {
                addCallback(bb);
            }
        }

        public void addCallback(ButtonBase buttonBase) {
            callback.add(buttonBase);
        }

        private void gotBitmapDrawable(final BitmapDrawable bd) {
            if (this.bd != null) {
                return;
            }
            this.bd = bd;
            vaContainer.$context().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (ButtonBase bb : callback) {
                        setImage(bb);
                    }
                }
            });
        }

        public void releaseMemory() {
            this.bd = null;
            this.callback = null;
            // After bd is set to null, all request are not going to triger startLoading()
        }
    }

    /**
     * Adapt different versions of Appinventor
     * The original problem caused by changes in: 
     *   github/mit-cml/appinventor-sources/95be2f2489910d552052606b3a38eff012e632ec
     *   appinventor/components/src/com/google/appinventor/components/runtime/AndroidViewComponent.java @ 72
     */
    public static void Visible(AndroidViewComponent avc, boolean visibility) {
        Class<?> claz = AndroidViewComponent.class;
        try {
            claz.getMethod("Visible", Boolean.class)
                .invoke(avc, (Boolean)visibility);
            return;
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }
        try {
            claz.getMethod("Visible", boolean.class)
                .invoke(avc, visibility);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }
    }
}
