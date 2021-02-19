package edu.cnm.deepdive.animals.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Animal {

  @Expose
  private String name;

  @Expose
  @SerializedName("image")
  private String imageUrl;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
