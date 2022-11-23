namespace PyxisKapriBack.DTOComponents
{
    public class SearchDTO
    {
        public string Name { get; set; }
        public SearchType SearchType { get; set; }
        public double Longitude { get; set; }
        public double Latitude { get; set; }
        public double Distance { get; set; } = Constants.Constants.DISTANCE; 
    }
}
