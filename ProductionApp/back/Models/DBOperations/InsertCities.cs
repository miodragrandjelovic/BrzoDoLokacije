namespace PyxisKapriBack.Models
{
    public partial class Database
    {
        public City InsertCity(string CityName, string CountryName)
        {
            Country country = GetCountry(CountryName); 

            if(country == null)
                country = InsertCountry(CountryName);

            City city = GetCity(CityName); 
            if (city == null)
            {
                Cities.Add(new City
                {
                    Name = CityName, 
                    CountryId = country.Id
                });
                SaveChanges();
            }

            return GetCity(CityName); 
        }
        public City GetCity(string CityName)
        {
            return Cities.Where(city => city.Name.Equals(CityName)).Include(city => city.Country).FirstOrDefault();
        }
        private void InsertCities()
        {
            InsertCity(Constants.Constants.UNKNWOWN, Constants.Constants.UNKNWOWN);
            #region 'Srbija'
            InsertCity("Beograd", "Srbija");
            InsertCity("Novi Sad", "Srbija");
            InsertCity("Niš", "Srbija");
            InsertCity("Kragujevac", "Srbija");
            InsertCity("Subotica", "Srbija");
            InsertCity("Kraljevo", "Srbija");
            InsertCity("Sremski Karlovci", "Srbija");
            #endregion

            #region 'Crna Gora'
            InsertCity("Podgorica", "Crna Gora");
            InsertCity("Budva", "Crna Gora");
            InsertCity("Herceg Novi", "Crna Gora");
            InsertCity("Kotor", "Crna Gora");
            InsertCity("Bar", "Crna Gora");
            #endregion

            #region 'Bosna i Hercegovina'
            InsertCity("Banja Luka", "Bosna i Hercegovina");
            InsertCity("Sarajevo", "Bosna i Hercegovina");
            InsertCity("Trebinje", "Bosna i Hercegovina");
            #endregion

            #region 'Hrvatska'
            InsertCity("Zagreb", "Hrvatska");
            InsertCity("Split", "Hrvatska");
            InsertCity("Dubrovnik", "Hrvatska");
            #endregion

            #region 'Mađarska'
            InsertCity("Budimpešta", "Mađarska");
            #endregion

            #region 'Nemačka'
            InsertCity("Berlin", "Nemačka");
            InsertCity("Minhen", "Nemačka");
            InsertCity("Hamburg", "Nemačka");
            #endregion

            #region 'Austrija'
            InsertCity("Beč", "Austrija");
            #endregion
        }
    }
}
