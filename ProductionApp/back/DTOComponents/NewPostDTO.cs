namespace PyxisKapriBack.DTOComponents
{
    public class NewPostDTO
    {
        public List<IFormFile>? Images { get;set; }
        public string Description { get; set; }
        public string Longitude { get; set; }
        public string Latitude { get; set; }
        public string LocationName { get; set; }
        public string Address { get; set; }
        public string City { get; set; }
        public string Country { get; set; }
        public string? Tags { get; set; }
    }
}
