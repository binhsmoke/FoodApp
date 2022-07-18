using System;
using System.Collections.Generic;

#nullable disable

namespace MasJoheun.Models
{
    public partial class Event
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public string Image { get; set; }
        public int IdRestaurant { get; set; }

        public virtual Restaurant IdRestaurantNavigation { get; set; }
    }
}
