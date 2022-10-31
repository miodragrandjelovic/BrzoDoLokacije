using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;
using PyxisKapriBack.Services.Interfaces;

namespace PyxisKapriBack.Services
{
    public class PlaceService : IPlaceService
    {
        private ILocationDAL _iLocationDAL; 

        public PlaceService(ILocationDAL iLocationDAL)
        {
            _iLocationDAL = iLocationDAL;
        }

        public List<Location> FilterLocations(string filter)
        {
            return _iLocationDAL.FilterLocations(filter);
        }

        public List<Location> GetNextSetOfLocations(int take = 5)
        {
            return _iLocationDAL.GetNextSetOfLocations(take);
        }
    }
}
