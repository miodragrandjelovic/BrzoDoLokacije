namespace PyxisKapriBack.Models
{
    public partial class Database
    {
        public Location InsertLocation(string LocationName, string CityName, double longitude = 0, double latitude = 0)
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
                    CityID = city.Id, 
                    Latitude = latitude,
                    Longitude = longitude
                });
                SaveChanges();
            }
            return GetLocation(LocationName); 
        }
        public Location GetLocation(string name)
        {
            return Locations.Where(x => x.Name.Equals(name)).Include(location => location.City)
                                                            .Include(location => location.Posts)
                                                            .Include(location => location.City.Country)
                                                            .FirstOrDefault();
        }
        private void InsertLocations()
        {
            InsertLocation(Constants.Constants.UNKNWOWN, Constants.Constants.UNKNWOWN);

            #region 'Srbija'
            #region 'Beograd'
            InsertLocation("Kalemegdan", "Beograd", 44.822534550242686, 20.450582962906765);
            InsertLocation("Tvrđava Gardoš", "Beograd", 44.84898510620935, 20.40930690793538);
            InsertLocation("Beogradski zoološki vrt", "Beograd", 44.825602585581876, 20.454367859032633);
            InsertLocation("Muzej savremene umetnosti", "Beograd", 44.819866697609854, 20.44245603046319);
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
