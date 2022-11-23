using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Services.Interfaces;
using PyxisKapriBack.UI.Interfaces;

namespace PyxisKapriBack.Controllers
{
    [Authorize(Roles ="User,Admin")]
    [Route("api/[controller]")]
    [ApiController]
    public class PlaceController : ControllerBase
    {
        private readonly IPlaceService placeService;
        private readonly IPlaceUI placeUI; 

        public PlaceController(IPlaceService placeService, IPlaceUI placeUI)
        {
            this.placeService = placeService;
            this.placeUI = placeUI;
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
        public async Task<IActionResult> GetAllAroundLocations(SearchDTO search)
        {
            var locations = new List<LocationDTO>();
            locations = placeUI.GetAllAroundLocations(search); 

            return Ok(locations);
        }
    }
}
