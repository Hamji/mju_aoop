package data.dto;

public class CGIDTO {
	//데이터값들
	private String country;
	private String countryCode;
	private String capital;
	private String climate;
	private String location;
	private String majorCity;
	private Religion religion;
	private MajorGroups majorGroups;
	private String media;
	private double area;
	private String areaSource;
	private String areaDescription;
	private String language;
	private int year;
	
	//생성자
	public CGIDTO() {}
	private CGIDTO(Builder builder) {
		this.country = builder.country;
		this.countryCode = builder.countryCode;
		this.capital = builder.capital;
		this.climate = builder.climate;
		this.location = builder.location;
		this.majorCity = builder.majorCity;
		this.religion = builder.religion;
		this.majorGroups = builder.majorGroups;
		this.media = builder.media;
		this.area = builder.area;
		this.areaSource = builder.areaSource;
		this.areaDescription = builder.areaDescription;
		this.language = builder.language;
		this.year = builder.year;
	}
	
	//Getter
	public String getCountry() {
		return this.country;
	}
	
	public String getCountryCode() {
		return this.countryCode;
	}
	
	public String getCapital() {
		return this.capital;
	}
	
	public String getClimate() {
		return this.climate;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public String getMajorCity() {
		return this.majorCity;
	}
	
	public Religion getReligion() {
		return this.religion;
	}
	
	public MajorGroups getMajorGroups() {
		return this.majorGroups;
	}
	
	public String getMedia() {
		return this.media;
	}
	
	public double getArea() {
		return this.area;
	}
	
	public String getAreaSource() {
		return this.areaSource;
	}
	
	public String getAreaDescription() {
		return this.areaDescription;
	}
	
	public String getLanguage() {
		return this.language;
	}
	
	public int getYear() {
		return this.year;
	}
	
	//내부클래스 Builder
	public static class Builder {
		//Builder를 통해 받아올 자료
		private String country;
		private String countryCode;
		private String capital;
		private String climate;
		private String location;
		private String majorCity;
		private Religion religion;
		private MajorGroups majorGroups;
		private String media;
		private double area;
		private String areaSource;
		private String areaDescription;
		private String language;
		private int year;
		
		//기본생성자
		public Builder() {}
		
		//Setter
		public Builder setCountry(String ctry) {
			this.country = ctry;
			return this;
		}
		
		public Builder setCountryCode(String code) {
			this.countryCode = code;
			return this;
		}
		
		public Builder setCapital(String cap) {
			this.capital = cap;
			return this;
		}
		public Builder setClimate(String cli) {
			this.climate = cli;
			return this;
		}
		
		public Builder setLocation(String lo) {
			this.location = lo;
			return this;
		}
		
		public Builder setMajorCity(String city) {
			this.majorCity = city;
			return this;
		}
		
		public Builder setReligion(Religion rg) {
			this.religion = rg;
			return this;
		}
		
		public Builder setMajorGroups(MajorGroups grp) {
			this.majorGroups = grp;
			return this;
		}
		
		public Builder setMedia(String me) {
			this.media = me;
			return this;
		}
		
		public Builder setArea(double area) {
			this.area = area;
			return this;
		}
		public Builder setAreaSource(String src) {
			this.areaSource = src;
			return this;
		}
		
		public Builder setAreaDescription(String des) {
			this.areaDescription = des;
			return this;
		}
		
		public Builder setLanguage(String lang) {
			this.language = lang;
			return this;
		}
		
		public Builder setYear(int year) {
			this.year = year;
			return this;
		}
		
		//build
		public CGIDTO build() {
			return new CGIDTO(this);
		}
	}
}