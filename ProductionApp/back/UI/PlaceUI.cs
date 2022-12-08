using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;
using PyxisKapriBack.Services.Interfaces;
using PyxisKapriBack.UI.Interfaces;

namespace PyxisKapriBack.UI
{
    public class PlaceUI : IPlaceUI
    {
        private readonly IPlaceService iPlaceService; 

        public PlaceUI(IPlaceService iPlaceService)
        {
            this.iPlaceService = iPlaceService;
        }

        public List<LocationDTO> FilterLocations(string location)
        {
            return iPlaceService.FilterLocations(location);
        }

        public List<LocationDTO> GetAllAroundLocations(SearchDTO search)
        {
            List <LocationDTO> locationsDTO = null;  
            List<Location> locations = null;
            double distance = Constants.Constants.DISTANCE; 
            if (search.Distance > 0)
                distance = search.Distance; 

            switch (search.SearchType)
            {
                case SearchType.LOCATION:
                    locationsDTO = iPlaceService.GetAllAroundLocationsByName(search.Name, distance);
                    break;
                case SearchType.COORDINATES: 
                    locationsDTO = iPlaceService.GetAllAroundLocationsByCoordinates(search.Longitude, search.Latitude, distance);
                    break;
                default:
                    locations = null;
                    break; 
            }

            return locationsDTO; 
        }

        public List<LocationDTO> GetNextSetOfLocations(int amountOfLocations)
        {
            throw new NotImplementedException();
        }
    }
}
