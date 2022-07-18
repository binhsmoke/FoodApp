using MasJoheun.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Linq;

namespace MasJoheun.Controllers
{
    [Route("/api/")]
    [ApiController]
    public class FoodTypeController : ControllerBase
    {
        private readonly MasjoheunEntities db;
        public FoodTypeController(MasjoheunEntities context)
        {
            db = context;
        }
        [Authorize]
        [Route("get-category")]
        [HttpGet]
        public IActionResult GetCategory()
        {
            var result = from f in db.FoodTypes.ToList() select f ;
            List<FoodType> types = new List<FoodType>();
            foreach(FoodType type in result)
            {
                types.Add(type);
            }
            if (types != null)
                return Ok(types);
            else
                return BadRequest();
        }
    }
}
