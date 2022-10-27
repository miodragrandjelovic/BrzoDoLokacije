namespace PyxisKapriBack.Models
{
    public class Role
    {
        [Key]
        public int Id { get; set; }
        [MaxLength(Constants.ModelConstants.LENGTH_SHORT_NAME)]
        public string Name { get; set; }    

        public List<User> Users { get; set; }
    }
}
