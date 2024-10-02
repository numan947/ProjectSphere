import React from "react";
import { Flex, Box, Text, Grid, Spacer } from "@chakra-ui/react";
import FilterList from "../components/FilterList";
import { FaFile, FaFilter, FaTags } from "react-icons/fa";
import { TiTags } from "react-icons/ti";
import ProjectCardList from "../components/ProjectCardList";

const Home = () => {
  const filters = ["All", "Active", "Completed"];
  const tags = ["Urgent", "High Priority", "Low Priority"];
  // Sample data for the lists
  const mainContent = Array.from(
    { length: 300 },
    (_, index) => `Main Item ${index + 1}`
  );
  const asideContent = Array.from(
    { length: 150 },
    (_, index) => `Aside Item ${index + 1}`
  );

  return (
    <Grid templateColumns="1fr 4fr" height="100vh">
      {/* Aside Section - Sticky */}
      <Box
        position="sticky"
        top={0} // Keeps the aside at the top of the viewport
        height="100vh"
        borderRight="1px solid"
        borderColor="gray.200"
        overflowY="auto"
        p={4}
      >
        <FilterList
          title="Categories"
          filters={filters}
          orientation="column"
          icon={<FaFilter />}
        />
        <FilterList
          title="Tags"
          filters={tags}
          orientation="column"
          icon={<TiTags />}
        />
      </Box>

      {/* Main Section */}
      <Box
        overflowY="auto" // Allows scrolling within the main content
        height="100vh"
        p={4}
      >
        <ProjectCardList />
      </Box>
    </Grid>
  );
};

export default Home;
