using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL.Interfaces
{
    public interface ICityDAL
    {
        public Boolean AddCity(string cityName, string countryName);
        public Boolean UpdateCity(City city);
        public Boolean DeleteCity(string cityName);
        public City GetCity(string cityName);
        public List<City> FilterCities(string filter);
    }
}