namespace PyxisKapriBack.Models
{
    public class Location
    {
        [Key]
        public int Id { get; set; }

        [MaxLength(Constants.ModelConstants.LENGTH_NAME)]
        public string Name { get; set; }
        public List<Post> Posts { get; set; }
        #region 'City'
        public int CityID { get; set; }
        public City City { get; set; }

        #endregion
    }
}
