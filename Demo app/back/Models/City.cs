namespace PyxisKapriBack.Models
{
    public class City
    {
        [Key]
        public int Id { get; set; }

        [MaxLength(Constants.ModelConstants.LENGTH_SHORT_NAME)]
        public string Name { get; set; }

        [ForeignKey("Country")]
        public int? CountryId{ get; set; }
        public Country Country { get; set; }
    }
}
