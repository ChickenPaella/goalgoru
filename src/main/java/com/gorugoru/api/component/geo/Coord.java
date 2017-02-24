package com.gorugoru.api.component.geo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coord{
	@JsonProperty("channel")
	private Channel channel;
	
	public Coord(){
	}
	
	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public static class Channel{
		private String title;
		private String description;
		private String generator;
		private String link;
		private String result;
		private String totalCount;
		private String pageCount;
		private String lastBuildDate;
		private List<Item> item;
		
		public Channel(){
		}
		
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getGenerator() {
			return generator;
		}

		public void setGenerator(String generator) {
			this.generator = generator;
		}

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}

		public String getTotalCount() {
			return totalCount;
		}

		public void setTotalCount(String totalCount) {
			this.totalCount = totalCount;
		}

		public String getPageCount() {
			return pageCount;
		}

		public void setPageCount(String pageCount) {
			this.pageCount = pageCount;
		}

		public String getLastBuildDate() {
			return lastBuildDate;
		}

		public void setLastBuildDate(String lastBuildDate) {
			this.lastBuildDate = lastBuildDate;
		}

		public List<Item> getItem() {
			return item;
		}

		public void setItem(List<Item> item) {
			this.item = item;
		}

		public static class Item{
			
			private String id;
			private String title;//전체 주소
			private String mountain;//산
			private String localName_1;//지역명1
			private String localName_2;//지역명2
			private String localName_3;//지역명3
			private String mainAddress;//주 번지
			private String subAddress;//부 번지
			private String buildingAddress;//건물 번지
			private String isNewAddress;//신주소 여부
			private String newAddress;//신주소
			private String zipcode;//구우편번호(6자리)
			private String zone_no;//새우편번호(5자리)
			private String placeName;
			private String point_wx;
			private String point_wy;
			private String point_x;
			private String point_y;
			private String lat;
			private String lng;
			
			public Item(){
			}

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getZone_no() {
				return zone_no;
			}

			public void setZone_no(String zone_no) {
				this.zone_no = zone_no;
			}

			public String getMountain() {
				return mountain;
			}

			public void setMountain(String mountain) {
				this.mountain = mountain;
			}

			public String getMainAddress() {
				return mainAddress;
			}

			public void setMainAddress(String mainAddress) {
				this.mainAddress = mainAddress;
			}

			public String getSubAddress() {
				return subAddress;
			}

			public void setSubAddress(String subAddress) {
				this.subAddress = subAddress;
			}

			public String getPoint_wx() {
				return point_wx;
			}

			public void setPoint_wx(String point_wx) {
				this.point_wx = point_wx;
			}

			public String getPoint_wy() {
				return point_wy;
			}

			public void setPoint_wy(String point_wy) {
				this.point_wy = point_wy;
			}

			public String getIsNewAddress() {
				return isNewAddress;
			}

			public void setIsNewAddress(String isNewAddress) {
				this.isNewAddress = isNewAddress;
			}

			public String getBuildingAddress() {
				return buildingAddress;
			}

			public void setBuildingAddress(String buildingAddress) {
				this.buildingAddress = buildingAddress;
			}

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getPlaceName() {
				return placeName;
			}

			public void setPlaceName(String placeName) {
				this.placeName = placeName;
			}

			public String getZipcode() {
				return zipcode;
			}

			public void setZipcode(String zipcode) {
				this.zipcode = zipcode;
			}

			public String getNewAddress() {
				return newAddress;
			}

			public void setNewAddress(String newAddress) {
				this.newAddress = newAddress;
			}

			public String getLocalName_1() {
				return localName_1;
			}

			public void setLocalName_1(String localName_1) {
				this.localName_1 = localName_1;
			}

			public String getLocalName_2() {
				return localName_2;
			}

			public void setLocalName_2(String localName_2) {
				this.localName_2 = localName_2;
			}

			public String getLocalName_3() {
				return localName_3;
			}

			public void setLocalName_3(String localName_3) {
				this.localName_3 = localName_3;
			}

			public String getLat() {
				return lat;
			}

			public void setLat(String lat) {
				this.lat = lat;
			}

			public String getPoint_x() {
				return point_x;
			}

			public void setPoint_x(String point_x) {
				this.point_x = point_x;
			}

			public String getLng() {
				return lng;
			}

			public void setLng(String lng) {
				this.lng = lng;
			}

			public String getPoint_y() {
				return point_y;
			}

			public void setPoint_y(String point_y) {
				this.point_y = point_y;
			}
			
		}
	}
}
