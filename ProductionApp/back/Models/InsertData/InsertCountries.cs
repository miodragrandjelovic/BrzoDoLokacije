
namespace PyxisKapriBack.Models
{
    public partial class Database
    {
        private void InsertCountries()
        {
            Country country = Countries.Where(x => x.Name.Contains("Srbija")).FirstOrDefault();
            if (country == null)
            {
                Countries.Add(new Country
                {
                    Name = "Srbija"
                });
                SaveChanges();
            }

            country = Countries.Where(x => x.Name.Contains("Crna Gora")).FirstOrDefault();
            if (country == null)
            {
                Countries.Add(new Country
                {
                    Name = "Crna Gora"
                });
                SaveChanges();
            }

            country = Countries.Where(x => x.Name.Contains("Bosna i Hercegovina")).FirstOrDefault();
            if (country == null)
            {
                Countries.Add(new Country
                {
                    Name = "Bosna i Hercegovina"
                });
                SaveChanges();
            }

            country = Countries.Where(x => x.Name.Contains("Severna Makedonija")).FirstOrDefault();
            if (country == null)
            {
                Countries.Add(new Country
                {
                    Name = "Severna Makedonija"
                });
                SaveChanges();
            }

            country = Countries.Where(x => x.Name.Contains("Hrvatska")).FirstOrDefault();
            if (country == null)
            {
                Countries.Add(new Country
                {
                    Name = "Hrvatska"
                });
                SaveChanges();
            }

            country = Countries.Where(x => x.Name.Contains("Slovenija")).FirstOrDefault();
            if (country == null)
            {
                Countries.Add(new Country
                {
                    Name = "Slovenija"
                });
                SaveChanges();
            }

            country = Countries.Where(x => x.Name.Contains("Mađarska")).FirstOrDefault();
            if (country == null)
            {
                Countries.Add(new Country
                {
                    Name = "Mađarska"
                });
                SaveChanges();
            }

            country = Countries.Where(x => x.Name.Contains("Rumunija")).FirstOrDefault();
            if (country == null)
            {
                Countries.Add(new Country
                {
                    Name = "Rumunija"
                });
                SaveChanges();
            }

            country = Countries.Where(x => x.Name.Contains("Bugarska")).FirstOrDefault();
            if (country == null)
            {
                Countries.Add(new Country
                {
                    Name = "Bugarska"
                });
                SaveChanges();
            }

            country = Countries.Where(x => x.Name.Contains("Rusija")).FirstOrDefault();
            if (country == null)
            {
                Countries.Add(new Country
                {
                    Name = "Rusija"
                });
                SaveChanges();
            }

            country = Countries.Where(x => x.Name.Contains("Italija")).FirstOrDefault();
            if (country == null)
            {
                Countries.Add(new Country
                {
                    Name = "Italija"
                });
                SaveChanges();
            }

            country = Countries.Where(x => x.Name.Contains("Španija")).FirstOrDefault();
            if (country == null)
            {
                Countries.Add(new Country
                {
                    Name = "Španija"
                });
                SaveChanges();
            }

            country = Countries.Where(x => x.Name.Contains("Francuska")).FirstOrDefault();
            if (country == null)
            {
                Countries.Add(new Country
                {
                    Name = "Francuska"
                });
                SaveChanges();
            }

            country = Countries.Where(x => x.Name.Contains("Austrija")).FirstOrDefault();
            if (country == null)
            {
                Countries.Add(new Country
                {
                    Name = "Austrija"
                });
                SaveChanges();
            }

            country = Countries.Where(x => x.Name.Contains("Nemačka")).FirstOrDefault();
            if (country == null)
            {
                Countries.Add(new Country
                {
                    Name = "Nemačka"
                });
                SaveChanges();
            }
        }
    }
}