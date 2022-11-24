namespace PyxisKapriBack.DTOComponents
{
    public class NewPostDTO
    {
        public IFormFile CoverImage { get; set; }
        public List<IFormFile> Images { get;set; }
        public string Description { get; set; }
        public double Longitude { get; set; }
        public double Latitude { get; set; }
        public string LocationName { get; set; }
        public string Address { get; set; }
        public string City { get; set; }
        public string Country { get; set; }

    }
}
