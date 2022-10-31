﻿using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
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

        [HttpGet("FilterLocations")]
        public async Task<IActionResult> FilterLocations(string location)
        {
            var locations = placeService.FilterLocations(location);
            return Ok(locations);
        }
        [HttpGet("GetNextLocations")]
        public async Task<IActionResult> GetNextLocations(int amountOfLocations)
        {
            var locations = placeService.GetNextSetOfLocations(amountOfLocations);
            return Ok(locations);
        }
    }
}
