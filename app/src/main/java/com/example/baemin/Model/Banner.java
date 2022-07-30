package com.example.baemin.Model;

public class Banner {
   String bannerId;
   int bannerImg;

   public Banner(String bannerId, int bannerImg) {
      this.bannerId = bannerId;
      this.bannerImg = bannerImg;
   }

   public String getBannerId() {
      return bannerId;
   }

   public void setBannerId(String bannerId) {
      this.bannerId = bannerId;
   }

   public int getBannerImg() {
      return bannerImg;
   }

   public void setBannerImg(int bannerImg) {
      this.bannerImg = bannerImg;
   }
}
