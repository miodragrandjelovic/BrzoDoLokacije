namespace PyxisKapriBack.DTOComponents
{
    public class NewPostDTO
    {
        public string CoverImage { get; set; }
        public List<string> Images { get;set; }
        public string Description { get; set; }
        public double Lognitude { get; set; }
        public double Latitude { get; set; }
        public string LocationName { get; set; }
        public string Address { get; set; }
        public string City { get; set; }
        public string Country { get; set; }

    }
}
