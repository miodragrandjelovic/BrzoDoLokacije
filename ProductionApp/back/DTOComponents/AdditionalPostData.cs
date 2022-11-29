namespace PyxisKapriBack.DTOComponents
{
    public class AdditionalPostData
    {
        public string Description { get; set; }
        public List<string> Images { get;set; }
        public double Longitude { get; set; }  
        public double Latitude { get; set; }
        public string Address { get; set; }
        public string City { get; set; }
        public string Country { get; set; }
        public int NumberOfComments { get; set; } = 0;
    }
}
