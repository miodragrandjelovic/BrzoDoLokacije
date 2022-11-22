using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Services.Interfaces;

namespace PyxisKapriBack.Controllers
{
    [Authorize(Roles ="User,Admin")]
    [Route("api/[controller]")]
    [ApiController]
    public class PlaceController : ControllerBase
    {
        private readonly IPlaceService placeService;

        public PlaceController(IPlaceService placeService)
        {
            this.placeService = placeService;
        }

        [HttpPost("FilterLocations")]
        public async Task<IActionResult> FilterLocations([FromBody] string location)
        {
            var locations = placeService.FilterLocations(location);
            return Ok(locations);
        }
        [HttpPost("GetNextLocations")]
        public async Task<IActionResult> GetNextLocations(int amountOfLocations)
        {
            var locations = placeService.GetNextSetOfLocations(amountOfLocations);
            return Ok(locations);
        }

        [HttpPost("GetAllAroundLocations")]
        public async Task<IActionResult> GetAllAroundLocations(LocationDTO location)
        {
            var locations = new List<LocationDTO>();
            if (location.Distance > 0)
                locations = placeService.GetAllAroundLocations(location.Name, location.Distance);
            else
                locations = placeService.GetAllAroundLocations(location.Name); 
            return Ok(locations);
        }
    }
}
