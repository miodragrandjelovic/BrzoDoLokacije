﻿using PyxisKapriBack.Models;

namespace PyxisKapriBack.DTOComponents
{
    public class LocationDTO
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public double Distance { get; set; }
        public List<PostDTO> Posts { get; set; }
    }
}
