namespace PyxisKapriBack.Models
{
    public partial class Database
    {
        private void InsertLocations()
        {
            Location location = Locations.Where(x => x.Name.Contains(Constants.Constants.UNKNWOWN)).FirstOrDefault();

            if (location == null)
            {
                Locations.Add(new Location
                {
                    Name = Constants.Constants.UNKNWOWN,
                    CityID = Constants.ModelConstants.DEFAULT
                });
                SaveChanges();
            }
        }
    }
}
