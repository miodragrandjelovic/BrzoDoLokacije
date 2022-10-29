namespace PyxisKapriBack.Models
{
    public partial class Database
    {
        private void InsertCities()
        {
            City city = Cities.Where(x => x.Name.Contains(Constants.Constants.UNKNWOWN)).FirstOrDefault();

            if (city == null)
            {
                Cities.Add(new City
                {
                    Name = Constants.Constants.UNKNWOWN,
                    CountryId = Constants.ModelConstants.DEFAULT
                });
                SaveChanges();
            }
        }
    }
}
