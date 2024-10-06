import {
  Badge,
  Box,
  Card,
  CardBody,
  CardHeader,
  Checkbox,
  CheckboxGroup,
  Divider,
  Flex,
  Skeleton,
  Stack,
  Text,
} from "@chakra-ui/react";

interface FilterListProps {
  title: string;
  filters: string[];
  isloading: boolean;
  orientation?: "column" | "row";
  icon?: React.ReactNode;
  colorScheme: string;
}

const FilterList = ({
  title,
  filters,
  orientation,
  icon,
  isloading,
  colorScheme,
}: FilterListProps) => {
  return (
    <>
      <Card padding={2} margin={3}>
        <CardHeader>
          <Flex justify="space-between">
            <Text fontWeight="bold" mb={2}>
              {title}
            </Text>
            {icon}
          </Flex>
          <Divider />
        </CardHeader>
        <CardBody>
          <Skeleton isLoaded={!isloading}>
            <CheckboxGroup colorScheme={colorScheme}>
              <Stack direction={orientation}>
                {filters.map((filter, index) => (
                  <Checkbox key={index} value={filter} pl={2}>
                    <Box display="flex" alignItems="center">
                      <Badge colorScheme={colorScheme}>{filter}</Badge>
                    </Box>
                  </Checkbox>
                ))}
              </Stack>
            </CheckboxGroup>
          </Skeleton>
        </CardBody>
      </Card>
    </>
  );
};

export default FilterList;
