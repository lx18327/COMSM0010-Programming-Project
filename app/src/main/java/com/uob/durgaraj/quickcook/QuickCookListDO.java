package com.uob.durgaraj.quickcook;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "quickcook-mobilehub-861197771-QuickCookList")

public class QuickCookListDO {
    private String _itemId;
    private String _ingredient1;
    private String _ingredient2;
    private String _ingredient3;
    private String _itemName;
    private String _webUrl;
    private String _youtubeUrl;

    @DynamoDBHashKey(attributeName = "Item Id")
    @DynamoDBAttribute(attributeName = "Item Id")
    public String getItemId() {
        return _itemId;
    }

    public void setItemId(final String _itemId) {
        this._itemId = _itemId;
    }
    @DynamoDBAttribute(attributeName = "Ingredient 1")
    public String getIngredient1() {
        return _ingredient1;
    }

    public void setIngredient1(final String _ingredient1) {
        this._ingredient1 = _ingredient1;
    }
    @DynamoDBAttribute(attributeName = "Ingredient 2")
    public String getIngredient2() {
        return _ingredient2;
    }

    public void setIngredient2(final String _ingredient2) {
        this._ingredient2 = _ingredient2;
    }
    @DynamoDBAttribute(attributeName = "Ingredient 3")
    public String getIngredient3() {
        return _ingredient3;
    }

    public void setIngredient3(final String _ingredient3) {
        this._ingredient3 = _ingredient3;
    }
    @DynamoDBAttribute(attributeName = "Item Name")
    public String getItemName() {
        return _itemName;
    }

    public void setItemName(final String _itemName) {
        this._itemName = _itemName;
    }
    @DynamoDBAttribute(attributeName = "WebUrl")
    public String getWebUrl() {
        return _webUrl;
    }

    public void setWebUrl(final String _webUrl) {
        this._webUrl = _webUrl;
    }
    @DynamoDBAttribute(attributeName = "YoutubeUrl")
    public String getYoutubeUrl() {
        return _youtubeUrl;
    }

    public void setYoutubeUrl(final String _youtubeUrl) {
        this._youtubeUrl = _youtubeUrl;
    }

}
