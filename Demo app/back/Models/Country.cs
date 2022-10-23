namespace PyxisKapriBack.Models
{
    public class Country
    {
        [Key]
        public long Id { get; set; }
        [MaxLength(Constants.ModelConstants.LENGTH_SHORT_NAME)]
        public string Name { get; set; }

    }
}
