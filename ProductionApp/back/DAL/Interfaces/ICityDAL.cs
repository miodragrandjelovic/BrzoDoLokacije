using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL.Interfaces
{
    public interface ICityDAL
    {
        public bool AddCity(string cityName, string countryName);
        public bool UpdateCity(City city);
        public bool DeleteCity(string cityName);
        public City GetCity(string cityName);
        public List<City> FilterCities(string filter);
    }
}