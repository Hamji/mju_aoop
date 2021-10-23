package data.dto;

public class C19DTO {
	//Covid-19 데이터 내에 들어있는 요소들
	private String fips;
	private String admin2;
	private String provinceState;
	private String contryRegion;
	private double lat;
	private double lon;
	private int confirmed;
	private int deaths;
	private int recovered;
	private int active;
	private String combined;
	private double incidentRate;
	private double fatality;
	
	//생성자
	public C19DTO() {}
	private C19DTO(Builder builder) {
		this.fips = builder.fips;
		this.admin2 = builder.admin2;
		this.provinceState = builder.provinceState;
		this.contryRegion = builder.contryRegion;
		this.lat = builder.lat;
		this.lon = builder.lon;
		this.confirmed = builder.confirmed;
		this.deaths = builder.deaths;
		this.recovered = builder.recovered;
		this.active = builder.active;
		this.combined = builder.combined;
		this.incidentRate = builder.incidentRate;
		this.fatality = builder.fatality;
	}
	
	//Getter
	public String getFIPS() {
		return this.fips;
	}
	public String getAdmin2() {
		return this.admin2;
	}
	public String getProvinceState() {
		return this.provinceState;
	}
	public String getContryRegion() {
		return this.contryRegion;
	}
	public double getLat() {
		return this.lat;
	}
	public double getLon() {
		return this.lon;
	}
	public int getConfirmed() {
		return this.confirmed;
	}
	public int getDeaths() {
		return this.deaths;
	}
	public int getRecovered() {
		return this.recovered;
	}
	public int getActive() {
		return this.active;
	}
	public String getCombined() {
		return this.combined;
	}
	public double getIncidentRate() {
		return this.incidentRate;
	}
	public double getFatality() {
		return this.fatality;
	}
	
	//내부클래스 Builder
	public static class Builder{
		//Builder를 통해 받아올 데이터
		private String fips;
		private String admin2;
		private String provinceState;
		private String contryRegion;
		private double lat;
		private double lon;
		private int confirmed;
		private int deaths;
		private int recovered;
		private int active;
		private String combined;
		private double incidentRate;
		private double fatality;
		
		//생성자
		public Builder() {}
		
		//Setter
		public Builder setFIPS(String fips) {
			this.fips = fips;
			return this;
		}
		public Builder setAdmin2(String admin2) {
			this.admin2 = admin2;
			return this;
		}
		public Builder setProvinceState(String ps) {
			this.provinceState = ps;
			return this;
		}
		public Builder setContryRegion(String cr) {
			this.contryRegion = cr;
			return this;
		}
		public Builder setLat(double lat) {
			this.lat = lat;
			return this;
		}
		public Builder setlon(double lon) {
			this.lon = lon;
			return this;
		}
		public Builder setConfirmed(int cnf) {
			this.confirmed = cnf;
			return this;
		}
		public Builder setDeaths(int deaths) {
			this.deaths = deaths;
			return this;
		}
		public Builder setRecovered(int recv) {
			this.recovered = recv;
			return this;
		}
		public Builder setActive(int active) {
			this.active = active;
			return this;
		}
		public Builder setCombined(String combined) {
			this.combined = combined;
			return this;
		}
		public Builder setIncidentRate(double incidentRate) {
			this.incidentRate = incidentRate;
			return this;
		}
		public Builder setFatality(double fatality) {
			this.fatality = fatality;
			return this;
		}
		//build
		public C19DTO build() {
			return new C19DTO(this);
		}
	}
}
