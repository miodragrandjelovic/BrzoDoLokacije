namespace PyxisKapriBack.Models
{
    public partial class Database
    {
        public Location InsertLocation(string LocationName, string CityName)
        {
            City city = GetCity(CityName);

            if (city == null)
                city = InsertCity(CityName, Constants.Constants.UNKNWOWN);

            Location location = GetLocation(LocationName);
            if (location == null)
            {
                Locations.Add(new Location
                {
                    Name = LocationName,
                    CityID = city.Id
                });
                SaveChanges();
            }
            return GetLocation(LocationName); 
        }
        public Location GetLocation(string name)
        {
            return Locations.Where(x => x.Name.Equals(name)).Include(location => location.City)
                                                            .Include(location => location.Posts)
                                                            .FirstOrDefault();
        }
        private void InsertLocations()
        {
            InsertLocation(Constants.Constants.UNKNWOWN, Constants.Constants.UNKNWOWN);

            #region 'Srbija'
            #region 'Beograd'
            InsertLocation("Kalemegdan", "Beograd");
            #endregion
            #region 'Novi Sad'
            InsertLocation("Petrovaradinska tvrđava", "Novi Sad");
            #endregion
            #region 'Novi Sad'
            InsertLocation("Niška tvrđava", "Niš");
            #endregion
            #region 'Kragujevac'
            InsertLocation("Šumarice", "Kragujevac");
            InsertLocation("Prva kragujevačka gimnazija", "Kragujevac");
            #endregion
            

            #endregion

        }
    }
}
