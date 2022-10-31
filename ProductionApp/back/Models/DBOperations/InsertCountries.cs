
namespace PyxisKapriBack.Models
{
    public partial class Database
    {
        public Country InsertCountry(string countryName)
        {
            Country country = GetCountry(countryName);
            if (country == null)
            {
                Countries.Add(new Country
                {
                    Name = countryName
                });
                SaveChanges();
            }

            return GetCountry(countryName); 
        }
        public Country GetCountry(string name)
        {
            return Countries.Where(x => x.Name.Equals(name)).FirstOrDefault();
        }
        private void InsertCountries()
        {
            InsertCountry(Constants.Constants.UNKNWOWN);
            InsertCountry("Srbija");
            InsertCountry("Crna Gora");
            InsertCountry("Bosna i Hercegovina");
            InsertCountry("Severna Makedonija");
            InsertCountry("Hrvatska");
            InsertCountry("Slovenija");
            InsertCountry("Mađarska");
            InsertCountry("Rumunija");
            InsertCountry("Bugarska");
            InsertCountry("Rusija");
            InsertCountry("Italija");
            InsertCountry("Španija");
            InsertCountry("Francuska");
            InsertCountry("Austrija");
            InsertCountry("Nemačka");
            InsertCountry("Holandija");
        }
    }
}