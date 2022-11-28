﻿namespace PyxisKapriBack.DTOComponents
{
    public class PostOnMapDTO
    {
        public int Id { get; set; }
        public string CoverImagePath { get; set; } = string.Empty;  
        public double Longitude { get; set; } = 0;
        public double Latitude { get; set; } = 0;
        public int numberOfLikes { get; set; } = 0; 
        public int numberOfViews { get; set; } = 0;

    }
}